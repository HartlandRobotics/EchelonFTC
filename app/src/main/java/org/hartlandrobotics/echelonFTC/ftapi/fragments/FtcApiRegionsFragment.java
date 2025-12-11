package org.hartlandrobotics.echelonFTC.ftapi.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.Evt;
import org.hartlandrobotics.echelonFTC.database.entities.Rgn;
import org.hartlandrobotics.echelonFTC.database.repositories.DistrictRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.ftapi.FtcApi;
import org.hartlandrobotics.echelonFTC.ftapi.FtcApiActivity;
import org.hartlandrobotics.echelonFTC.ftapi.FtcApiInterface;
import org.hartlandrobotics.echelonFTC.ftapi.models.FtcApiEvents;
import org.hartlandrobotics.echelonFTC.ftapi.status.ApiStatus;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FtcApiRegionsFragment extends Fragment {
    private RecyclerView regionRecycler;
    private RegionListAdapter regionListAdapter;
    private Button regionFetchButton;
    private TextView errorTextDisplay;

    public FtcApiRegionsFragment() {
        //required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_ftcapi_regions, container, false);

        regionFetchButton = fragmentView.findViewById(R.id.regionPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);
        errorTextDisplay.setVisibility(View.GONE);

        setupCurrentRegions();

        setupPullRegions();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        regionListAdapter = new RegionListAdapter( getActivity() );

        regionRecycler = view.findViewById(R.id.region_recycler);
        regionRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        regionRecycler.setAdapter(regionListAdapter);
        regionRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
    }

    public void setupPullRegions() {

        regionFetchButton.setOnClickListener((view) -> {
            Application app = getActivity().getApplication();
            Context appContext = app.getApplicationContext();

            OrangeAllianceStatus status = new OrangeAllianceStatus(app);
            status.loadSettingsFromPrefs();
            FtcApiInterface ftcApi = FtcApi.getApiClient(app);

            try {
                Call<FtcApiEvents> newCall = ftcApi.getEvents(status.getYear());
                newCall.enqueue(new Callback<FtcApiEvents>() {
                    @Override
                    public void onResponse(Call<FtcApiEvents> call, Response<FtcApiEvents> response) {
                        try {
                            if (!response.isSuccessful()) {
                                errorTextDisplay.setText("Couldn't pull regions");
                            } else {
                                FtcApiEvents ftcapievents = response.body();
                                List<Evt> events = ftcapievents.getEvents()
                                        .stream()
                                        .map(ftcapievt -> ftcapievt.toEvent(appContext,status.getYear()))
                                        .collect(Collectors.toList());

                                List<Rgn> regions =  events.stream()
                                        .map(Evt::getRegionKey)
                                        .distinct()
                                        .map(strRegion -> new Rgn(strRegion, strRegion))
                                        .collect(Collectors.toList());

                                EventRepo eventRepo = new EventRepo(FtcApiRegionsFragment.this.getActivity().getApplication());
                                eventRepo.upsert(events);

                                DistrictRepo regionRepo = new DistrictRepo(FtcApiRegionsFragment.this.getActivity().getApplication());
                                regionRepo.upsert(regions);

                                for(Evt event : events ){
                                    eventRepo.upsert(event.toRgnEvent(event.getRegionKey()));
                                }

                                // convert entity districts to district view model merging
                                // with status to get the current distruct
                                // so the is selected can be set correctly
                                regionListAdapter.setRegions(regions);

                                errorTextDisplay.setText("Got events " + events.size());

                            }
                        } catch (Exception e) {
                            errorTextDisplay.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<FtcApiEvents> call, Throwable t) {
                        errorTextDisplay.setText("Couldn't pull events");
                    }
                });
            } catch (Exception e) {
                errorTextDisplay.setText("Error second catch " + e.getMessage());
            }
        });
    }

    public void setupCurrentRegions(){
        //Context appContext = getActivity().getApplicationContext();
        //BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        // int currentYear = Integer.parseInt(status.getYear());
        DistrictRepo districtRepo = new DistrictRepo(FtcApiRegionsFragment.this.getActivity().getApplication());
        districtRepo.getDistricts().observe(getViewLifecycleOwner(), regions -> {
            regionListAdapter.setRegions(regions);
        });
        //districtRepo.getDistrictsByYear(currentYear).observe(getViewLifecycleOwner(), districts -> {
        //    districtListAdapter.setDistricts(districts);
        //});
    }

    public class RegionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final MaterialTextView regionName;
        private final MaterialTextView regionKey;
        private final MaterialRadioButton regionSelectedRadioButton;

        private FtcApiRegionViewModel regionViewModel;

        RegionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            regionName = itemView.findViewById(R.id.district_display_name);
            regionKey = itemView.findViewById(R.id.district_key);
            regionSelectedRadioButton = itemView.findViewById(R.id.district_selected_radiobutton);

            regionSelectedRadioButton.setOnClickListener(v -> {
                regionListAdapter.setCurrentRegion( regionViewModel );
            });
        }

        public void setRegion(FtcApiRegionViewModel regionViewModel) {
            this.regionViewModel = regionViewModel;

            regionName.setText(regionViewModel.getDescription());
            regionKey.setText(regionViewModel.getRegionKey());
            regionSelectedRadioButton.setChecked(regionViewModel.getIsSelected());
        }

        public void setDisplayText(String displayText) {
            regionName.setText(displayText);
        }

        @Override
        public void onClick(View view) {
            regionListAdapter.setCurrentRegion(regionViewModel);
        }

    }







    public class RegionListAdapter extends RecyclerView.Adapter<RegionViewHolder> {
        private final LayoutInflater inflater;
        private List<FtcApiRegionViewModel> regionViewModels;

        RegionListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public RegionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_district, parent, false);
            return new RegionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RegionViewHolder holder, int position) {
            if (regionViewModels != null) {
                holder.setRegion(regionViewModels.get(position));
            } else {
                holder.setDisplayText("No Region Data Yet...");
            }
        }

        void setRegions(List<Rgn> regions) {
            Context appContext = getActivity().getApplicationContext();
            ApiStatus status = new ApiStatus(appContext);
            String currentRegionKey = status.getRegionKey();

            regionViewModels = new ArrayList<>();
            for( Rgn region : regions ){
                FtcApiRegionViewModel viewModel = new FtcApiRegionViewModel(region);
                if( region.getRegionKey().equals(currentRegionKey) ){
                    viewModel.setIsSelected(true);
                    setCurrentRegion(viewModel);
                }
                regionViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentRegion(FtcApiRegionViewModel currentViewModel){
            FtcApiActivity ftcApiActivity = (FtcApiActivity)getActivity();
            ftcApiActivity.setRegionKey(currentViewModel.getRegionKey());

            for(FtcApiRegionViewModel viewModel : regionViewModels ){
                viewModel.setIsSelected( currentViewModel.getRegionKey().equals(viewModel.getRegionKey()));
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return regionViewModels == null ? 0 : regionViewModels.size();
        }
    }

}


/*


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.orangeAlliance.Api;
import org.hartlandrobotics.echelonFTC.orangeAlliance.ApiInterface;
import org.hartlandrobotics.echelonFTC.orangeAlliance.OrangeAllianceActivity;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncDistrict;
import org.hartlandrobotics.echelonFTC.database.entities.Rgn;
import org.hartlandrobotics.echelonFTC.database.repositories.DistrictRepo;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DistrictsFragment extends Fragment {













    private void showError(String errorMessage){

    }



}


 */