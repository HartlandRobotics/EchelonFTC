package org.hartlandrobotics.echelon2.TBA.fragments;

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
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.TBAActivity;
import org.hartlandrobotics.echelon2.TBA.models.SyncDistrict;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DistrictsFragment extends Fragment {
    private Button districtFetchButton;

    private RecyclerView districtRecycler;
    private DistrictListAdapter districtListAdapter;

    private TextView errorTextDisplay;


    public DistrictsFragment() {
        //required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_districts, container, false);

        districtFetchButton = fragmentView.findViewById(R.id.districtPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);
        errorTextDisplay.setVisibility(View.GONE);

        setupCurrentDistricts();

        setupPullDistricts();

        return fragmentView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        districtListAdapter = new DistrictListAdapter( getActivity() );

        districtRecycler = view.findViewById(R.id.district_recycler);
        districtRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        districtRecycler.setAdapter(districtListAdapter);
        districtRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
    }

    public void setupCurrentDistricts(){
        Context appContext = getActivity().getApplicationContext();
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        int currentYear = Integer.parseInt( status.getYear() );
        DistrictRepo districtRepo = new DistrictRepo(DistrictsFragment.this.getActivity().getApplication());
        districtRepo.getDistrictsByYear(currentYear).observe(getViewLifecycleOwner(), districts -> {
            districtListAdapter.setDistricts(districts);
        });
    }

    public void setupPullDistricts() {
        districtFetchButton.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try {
                Context appContext = getActivity().getApplicationContext();
                BlueAllianceStatus status = new BlueAllianceStatus(appContext);
                int currentYear = Integer.parseInt( status.getYear() );

                Call<List<SyncDistrict>> newCall = newApi.getDistrictsByYear(currentYear);
                newCall.enqueue(new Callback<List<SyncDistrict>>() {
                    @Override
                    public void onResponse(Call<List<SyncDistrict>> call, Response<List<SyncDistrict>> response) {
                        try {
                            if (!response.isSuccessful()) {
                                errorTextDisplay.setText("Couldn't pull districts");
                            } else {
                                DistrictRepo districtRepo = new DistrictRepo(DistrictsFragment.this.getActivity().getApplication());
                                List<SyncDistrict> syncDistricts = response.body();
                                List<District> districts = syncDistricts.stream()
                                        .map(district -> district.toDistrict())
                                        .collect(Collectors.toList());

                                districtRepo.upsert(districts);
                                // convert entity districts to district view model merging
                                // with status to get the current distruct
                                // so the is selected can be set correctly
                                districtListAdapter.setDistricts(districts);

                                errorTextDisplay.setText("Got districts " + syncDistricts.size());

                            }
                        } catch (Exception e) {
                            errorTextDisplay.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncDistrict>> call, Throwable t) {
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

    public class DistrictViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView districtName;
        private MaterialTextView districtKey;
        private MaterialRadioButton districtSelectedRadioButton;

        private DistrictListViewModel districtViewModel;

        DistrictViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            districtName = itemView.findViewById(R.id.district_display_name);
            districtKey = itemView.findViewById(R.id.district_key);
            districtSelectedRadioButton = itemView.findViewById(R.id.district_selected_radiobutton);

            districtSelectedRadioButton.setOnClickListener(v -> {
                districtListAdapter.setCurrentDistrict( districtViewModel );
            });
        }

        public void setDistrict(DistrictListViewModel districtViewModel) {
            this.districtViewModel = districtViewModel;

            districtName.setText(districtViewModel.getDisplayName());
            districtKey.setText(districtViewModel.getDistrictKey());
            districtSelectedRadioButton.setChecked(districtViewModel.getIsSelected());
        }

        public void setDisplayText(String displayText) {
            districtName.setText(displayText);
        }

        @Override
        public void onClick(View view) {
            districtListAdapter.setCurrentDistrict(districtViewModel);
            //districtSelectedCheckbox.setChecked(true);
            //districtViewModel.setIsSelected(true);
            // push up to activity to clear the rest?

        }

        //private void onSelectDistrict(DistrictListViewModel viewModel){
            //setDistrict(viewModel);

        //}

    }
    public class DistrictListAdapter extends RecyclerView.Adapter<DistrictViewHolder> {


        private final LayoutInflater inflater;
        private List<DistrictListViewModel> districtViewModels;

        DistrictListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_district, parent, false);
            return new DistrictViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
            if (districtViewModels != null) {
                holder.setDistrict(districtViewModels.get(position));
            } else {
                holder.setDisplayText("No District Data Yet...");
            }
        }

        void setDistricts(List<District> districts) {
            Context appContext = getActivity().getApplicationContext();
            BlueAllianceStatus status = new BlueAllianceStatus(appContext);
            String currentDistrictKey = status.getDistrictKey();

            districtViewModels = new ArrayList<>();
            for( District district : districts ){
                DistrictListViewModel viewModel = new DistrictListViewModel(district);
                if( district.getDistrictKey().equals(currentDistrictKey) ){
                    viewModel.setIsSelected(true);
                }
                districtViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        void setCurrentDistrict(DistrictListViewModel currentViewModel){
            TBAActivity tbaActivity = (TBAActivity)getActivity();
            tbaActivity.setDistrictKey(currentViewModel.getDistrictKey());

            for(DistrictListViewModel viewModel : districtViewModels ){
                viewModel.setIsSelected( currentViewModel.getDistrictKey().equals(viewModel.getDistrictKey()));
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return districtViewModels == null ? 0 : districtViewModels.size();
        }
    }

}
