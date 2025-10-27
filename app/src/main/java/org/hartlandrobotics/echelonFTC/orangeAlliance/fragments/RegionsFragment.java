package org.hartlandrobotics.echelonFTC.orangeAlliance.fragments;

import android.app.Activity;
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
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.orangeAlliance.Api;
import org.hartlandrobotics.echelonFTC.orangeAlliance.ApiInterface;
import org.hartlandrobotics.echelonFTC.orangeAlliance.ApiActivity;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncEvent;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncRegions;
import org.hartlandrobotics.echelonFTC.database.entities.Rgn;
import org.hartlandrobotics.echelonFTC.database.repositories.RegionRepo;
import org.hartlandrobotics.echelonFTC.status.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegionsFragment extends Fragment {
    private Button regionFetchButton;

    private RecyclerView regionRecycler;
    private RegionListAdapter regionListAdapter;

    private TextView errorTextDisplay;


    public RegionsFragment() {
        //required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_regions, container, false);

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

        regionRecycler = view.findViewById(R.id.district_recycler);
        regionRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        regionRecycler.setAdapter(regionListAdapter);
        regionRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
    }

    public void setupCurrentRegions(){
        Activity activity = getActivity();
        assert activity != null;
        Application app = activity.getApplication();

        RegionRepo regionRepo = new RegionRepo(app);
        regionRepo.getRegions().observe(getViewLifecycleOwner(), regions -> {
                regionListAdapter.setRegions(regions);
            });
    }

    public void setupPullRegions() {
        regionFetchButton.setOnClickListener((view) -> {
            Activity activity = getActivity();
            assert activity != null;
            Application app = activity.getApplication();
            Context appContext = activity.getApplicationContext();
            ApiInterface newApi = Api.getApiClient(app);

            try {
                ApiStatus status = new ApiStatus(appContext);
                Call<List<SyncEvent>> eventsCall = newApi.getEventsByYear();
                eventsCall.enqueue(new Callback<List<SyncEvent>>() {
                    @Override
                    public void onResponse(Call<List<SyncEvent>> call, Response<List<SyncEvent>> response) {
                        try {
                            if (!response.isSuccessful()) {
                                errorTextDisplay.setText("Couldn't pull events");
                            } else {
                                EventRepo eventRepo = new EventRepo(app);
                                List<SyncEvent> syncEvents = response.body();
                                List<Evt> events = syncEvents.stream()
                                        .map(SyncEvent::toEvent)
                                        .collect(Collectors.toList());
                                eventRepo.upsert(events);

                                List<Rgn> regions = events.stream()
                                        .map(Evt::getRegionCode)
                                        .distinct()
                                        .map(Rgn::new)
                                        .collect(Collectors.toList());

                                RegionRepo regionRepo = new RegionRepo(app);
                                regionRepo.upsert(regions);

                                regionListAdapter.setRegions(regions);

                                errorTextDisplay.setText("Got regions " + regions.size());

                            }
                        } catch (Exception e) {
                            errorTextDisplay.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncRegions>> call, Throwable t) {
                        errorTextDisplay.setText("Couldn't pull districts");
                    }
                });
            } catch (Exception e) {
                errorTextDisplay.setText("Error second catch " + e.getMessage());
            }
        });
    }

    private void showError(String errorMessage){

    }

    public class RegionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView regionCode;
        private MaterialRadioButton regionSelectedRadioButton;

        private RegionListViewModel regionViewModel;

        RegionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            regionCode = itemView.findViewById(R.id.region_code);
            regionSelectedRadioButton = itemView.findViewById(R.id.region_selected_radiobutton);

            regionSelectedRadioButton.setOnClickListener(v -> {
                regionListAdapter.setCurrentRegion( regionViewModel );
            });
        }

        public void setRegion(RegionListViewModel regionViewModel) {
            this.regionViewModel = regionViewModel;

            regionCode.setText(regionViewModel.getRegionCode());
            regionSelectedRadioButton.setChecked(regionViewModel.getIsSelected());
        }


        @Override
        public void onClick(View view) {
            regionListAdapter.setCurrentRegion(regionViewModel);
        }

    }
    public class RegionListAdapter extends RecyclerView.Adapter<RegionViewHolder> {
        private final LayoutInflater inflater;
        private List<RegionListViewModel> regionViewModels;

        RegionListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public RegionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_region, parent, false);
            return new RegionViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull RegionViewHolder holder, int position) {
            if (regionViewModels != null) {
                holder.setRegion(regionViewModels.get(position));
            } else {
                //holder.setDisplayText("No District Data Yet...");
            }
        }

        void setRegions(List<Rgn> regionss) {
            Context appContext = getActivity().getApplicationContext();
            ApiStatus status = new ApiStatus(appContext);
            String currentRegionCode = status.getRegionCode();

            regionViewModels = new ArrayList<>();
            for( Rgn region : regionss ){
                RegionListViewModel viewModel = new RegionListViewModel(region);
                if( region.getRegionCode().equals(currentRegionCode) ){
                    viewModel.setIsSelected(true);
                }
                regionViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentRegion(RegionListViewModel currentViewModel){
            ApiActivity orangeAllianceActivity = (ApiActivity)getActivity();
            orangeAllianceActivity.setRegionCode(currentViewModel.getRegionCode());

            for(RegionListViewModel viewModel : regionViewModels ){
                viewModel.setIsSelected( currentViewModel.getRegionCode().equals(viewModel.getRegionCode()));
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return districtViewModels == null ? 0 : districtViewModels.size();
        }
    }

}
