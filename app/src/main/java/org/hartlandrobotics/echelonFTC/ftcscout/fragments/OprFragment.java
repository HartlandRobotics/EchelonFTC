package org.hartlandrobotics.echelonFTC.ftcscout.fragments;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.database.entities.Opr;
import org.hartlandrobotics.echelonFTC.database.repositories.OprRepo;
import org.hartlandrobotics.echelonFTC.ftcapi.status.FtcApiStatus;
import org.hartlandrobotics.echelonFTC.ftcscout.FTCScoutApi;
import org.hartlandrobotics.echelonFTC.ftcscout.FTCScoutInterface;
import org.hartlandrobotics.echelonFTC.ftcscout.models.FtcScoutTeamOpr;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OprFragment extends Fragment {

    private static final String TAG = "OprFragment";

    private Button oprFetchButton;

    private OprListAdapter oprListAdapter;

    public OprFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_opr, container, false);

        oprFetchButton = fragmentView.findViewById(R.id.oprPullButton);

        setupCurrentOpr();
        setupPullOpr();

        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oprListAdapter = new OprListAdapter(getActivity());

        RecyclerView oprRecycler = view.findViewById(R.id.opr_recycler);
        oprRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        oprRecycler.setAdapter(oprListAdapter);
        oprRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }

    public void setupCurrentOpr(){
        Context appContext = getActivity().getApplicationContext();
        FtcApiStatus status = new FtcApiStatus(appContext);
        //String currentEventKey = status.getEventKey();
        OprRepo oprRepo = new OprRepo(OprFragment.this.getActivity().getApplication());
        oprRepo.getOprs().observe(getViewLifecycleOwner(), oprs -> {
            oprListAdapter.setOprs(oprs);
        });
    }

    public void setupPullOpr() {
            oprFetchButton.setOnClickListener(view -> {
                Application app = requireActivity().getApplication();
                Context context = app.getApplicationContext();
                FTCScoutInterface newApi = FTCScoutApi.getFtcScoutClient(context);

                try{
                    FtcApiStatus status = new FtcApiStatus(context);

                    retrofit2.Call<List<FtcScoutTeamOpr>> newCall = newApi.getOpr(status.getYear(), status.getEventCode());
                    newCall.enqueue(new Callback<List<FtcScoutTeamOpr>>() {
                        @Override
                        public void onResponse(@NonNull retrofit2.Call<List<FtcScoutTeamOpr>> call, @NonNull Response<List<FtcScoutTeamOpr>> response) {
                            Log.i(TAG, "Successfully got response for oprs");

                            try{
                                if(!response.isSuccessful()){
                                    Log.i(TAG,"Couldn't pull oprs");
                                }
                                else{
                                    OprRepo oprRepo = new OprRepo(app);
                                    List<FtcScoutTeamOpr> teamOprs = response.body();
                                    if( teamOprs == null ) return;

                                    List<Opr> oprs = teamOprs.stream()
                                    ///        .filter(score -> score.getTournamentLevel().equals("Quals"))
                                            .map(FtcScoutTeamOpr::toOpr)
                                            .sorted(Comparator.comparingInt(Opr::getTeamNumber))
                                            .collect(Collectors.toList());

                                    oprRepo.upsert(oprs);
                                    oprListAdapter.setOprs(oprs);
                                    //matchScoreListAdapter.setScores(scores);
                                }
                            }
                            catch(Exception e){
                                Log.e(TAG,"Error " + e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<List<FtcScoutTeamOpr>> call, @NonNull Throwable t) {
                            Log.i(TAG, "Failed to get response for scores");
                        }
                    });
                }
                catch(Exception e){
                    Log.i(TAG, "Error second catch " + e.getMessage());
                }
            });
    }

    public class OprViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialTextView teamKeyText;
        private MaterialTextView oprText;
        private MaterialTextView foulText;

        private Opr opr;

        OprViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            teamKeyText = itemView.findViewById(R.id.team_key);
            oprText = itemView.findViewById(R.id.totalPoints);
            foulText = itemView.findViewById(R.id.foulPoints);
        }

        public void setOpr(Opr opr) {
            this.opr = opr;
            teamKeyText.setText(String.valueOf(this.opr.getTeamNumber()));
            oprText.setText(String.format("%.3f", this.opr.getOpr()));
            foulText.setText(String.format("%.3f", this.opr.getFoul()));
        }

        public void setTeamKeyText(String displayText) {
            teamKeyText.setText(displayText);
        }

        @Override
        public void onClick(View view){}
    }

    public class OprListAdapter extends RecyclerView.Adapter<OprViewHolder> {
        private final LayoutInflater inflater;
        private List<Opr> oprs;

        OprListAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public OprViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_opr, parent, false);
            return new OprViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull OprViewHolder holder, int position) {
            if (oprs != null) {
                holder.setOpr(oprs.get(position));
            } else {
                holder.setTeamKeyText("No Team Data Yet...");
            }
        }

        void setOprs(List<Opr> oprsPara) {
            oprs = new ArrayList<>();
            oprs.addAll(oprsPara);

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if( oprs != null ) return oprs.size();
            return 0;
        }
    }
}