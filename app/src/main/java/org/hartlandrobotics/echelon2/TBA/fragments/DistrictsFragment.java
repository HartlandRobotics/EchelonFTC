package org.hartlandrobotics.echelon2.TBA.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.TBAActivity;
import org.hartlandrobotics.echelon2.TBA.models.SyncDistrict;
import org.hartlandrobotics.echelon2.database.entities.District;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DistrictsFragment extends Fragment {
    public DistrictsFragment() {
        //required empty constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private TextView errorTextDisplay;
    Button districtPull;

    private RecyclerView districtRecycler;
    private DistrictListAdapter districtListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_districts, container, false);
        districtPull = fragmentView.findViewById(R.id.districtPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);

        setupPullDistricts();


        return fragmentView;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        districtListAdapter = new DistrictListAdapter( getActivity() );

        districtRecycler = view.findViewById(R.id.district_recycler);
        districtRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        districtRecycler.setAdapter(districtListAdapter);
    }

    public void setupPullDistricts() {
        districtPull.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try {
                Call<List<SyncDistrict>> newCall = newApi.getDistrictsByYear(2022);
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

    public class DistrictViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextInputLayout districtNameLayout;
        private EditText districtName;
        String districtKey;
        boolean isChecked = false;
        private District district;

        private DistrictViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            districtNameLayout = itemView.findViewById(R.id.list_item_textview);
            districtNameLayout.setEndIconDrawable(android.R.drawable.checkbox_off_background);
            districtName = districtNameLayout.getEditText();

            districtNameLayout.setEndIconOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TBAActivity a = (TBAActivity)getActivity();
                    a.setDistrictKey(district.getDistrictKey());

                    isChecked = !isChecked;
                    if(isChecked){
                        districtNameLayout.setEndIconDrawable(android.R.drawable.checkbox_on_background);
                        
                    }
                    else{
                        districtNameLayout.setEndIconDrawable(android.R.drawable.checkbox_off_background);
                    }
                }
            });
        }

        public void setDistrict(District district) {
            this.district = district;

            districtName.setText(district.getDisplayName());
            districtNameLayout.setHelperText(district.getDistrictKey());
        }

        public void setDisplayText(String displayText) {
            districtName.setText(displayText);
        }

        @Override
        public void onClick(View view) {
            districtKey = this.district.getDistrictKey();
        }

    }

    public class DistrictListAdapter extends RecyclerView.Adapter<DistrictViewHolder> {


        private final LayoutInflater mInflater;
        private List<District> mDistricts; // cached copy of districts

        DistrictListAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = mInflater.inflate(R.layout.list_item, parent, false);
            return new DistrictViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull DistrictViewHolder holder, int position) {
            if (mDistricts != null) {
                holder.setDistrict(mDistricts.get(position));
            } else {
                holder.setDisplayText("No District Data Yet...");
            }
        }

        void setDistricts(List<District> districts) {
            mDistricts = districts;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if( mDistricts != null ) return mDistricts.size();
            return 0;
        }
    }
}
