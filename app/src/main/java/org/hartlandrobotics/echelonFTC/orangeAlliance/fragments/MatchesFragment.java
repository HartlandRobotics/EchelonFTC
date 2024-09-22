package org.hartlandrobotics.echelonFTC.orangeAlliance.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.hartlandrobotics.echelonFTC.R;
import org.hartlandrobotics.echelonFTC.orangeAlliance.Api;
import org.hartlandrobotics.echelonFTC.orangeAlliance.ApiInterface;
import org.hartlandrobotics.echelonFTC.orangeAlliance.models.SyncMatch;
import org.hartlandrobotics.echelonFTC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.Match;
import org.hartlandrobotics.echelonFTC.database.repositories.EventRepo;
import org.hartlandrobotics.echelonFTC.database.repositories.MatchRepo;
import org.hartlandrobotics.echelonFTC.status.OrangeAllianceStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MatchesFragment extends Fragment {
    private static final String TAG = "MatchesFragment";
    private Button matchFetchButton;
    private RecyclerView matchRecycler;
    private MatchListAdapter matchListAdapter;
    //private TextView errorTextDisplay;

    public MatchesFragment(){
        // required empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){ super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View fragmentView = inflater.inflate(R.layout.fragment_matches, container, false);

        matchFetchButton = fragmentView.findViewById(R.id.matchPullButton);
        //errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);
        //errorTextDisplay.setVisibility(View.GONE);

        return fragmentView;
    }

    @Override
    public void onResume(){
        super.onResume();
        setupCurrentMatches();
        setupPullMatches();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        matchListAdapter = new MatchListAdapter(getActivity());

        matchRecycler = view.findViewById(R.id.match_recycler);
        matchRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        matchRecycler.setAdapter(matchListAdapter);
        matchRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));

    }

    public void setupCurrentMatches(){
        Context appContext = getActivity().getApplicationContext();
        OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
        String eventKey = status.getEventKey();
        EventRepo eventRepo = new EventRepo(MatchesFragment.this.getActivity().getApplication());
        eventRepo.getEventWithMatchs(eventKey).observe(getViewLifecycleOwner(), events -> {
            List<Match> matches = events != null ? events.matches : new ArrayList<>();
            matchListAdapter.setMatches(matches);
        });

    }

    public  void setupPullMatches(){
        matchFetchButton.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Context appContext = getActivity().getApplicationContext();
                OrangeAllianceStatus status = new OrangeAllianceStatus(appContext);
                String eventKey = status.getEventKey();

                Call<List<SyncMatch>> newCall = newApi.getMatchesByEvent(eventKey);
                newCall.enqueue(new Callback<List<SyncMatch>>(){
                    @Override
                    public void onResponse(Call<List<SyncMatch>> call, Response<List<SyncMatch>> response){
                        try{
                            if(!response.isSuccessful()){
                                Log.e(TAG, "Couldn't pull matches" );
                            }else{
                                MatchRepo matchRepo = new MatchRepo(MatchesFragment.this.getActivity().getApplication());
                                List<SyncMatch> syncMatches = response.body();
                                List<Match> matches = syncMatches.stream()
                                        .filter( match -> match.getTournamentLevel() == 1)
                                        .map(match -> match.toMatch())
                                        .collect(Collectors.toList());

                                matchRepo.upsert(matches);

                                for(Match m : matches){
                                    EvtMatchCrossRef crossRef = new EvtMatchCrossRef(eventKey, m.getMatchKey());
                                    matchRepo.upsert(crossRef);

                                }

                                matchListAdapter.setMatches(matches);

                                Log.i(TAG, "Got matches " + syncMatches.size());
                            }
                        }catch(Exception e){
                            Log.e(TAG, "Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncMatch>> call, Throwable t){
                        Log.e(TAG, "Couldn't pull matches");
                    }
                });
            }catch(Exception e){
                Log.e(TAG, "Error second catch " + e.getMessage());
            }
        });
    }

    private void showError(String errorMessage){

    }

    public class MatchViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView matchNumber;
        private MaterialTextView matchKey;
        private MaterialTextView red1;
        private MaterialTextView red2;
        private MaterialTextView blue1;
        private MaterialTextView blue2;

        private MatchListViewModel matchViewModel;

        MatchViewHolder(View itemView){
            super(itemView);

            matchNumber = itemView.findViewById(R.id.match_number);
            red1 = itemView.findViewById(R.id.red1);
            red2 = itemView.findViewById(R.id.red2);
            blue1 = itemView.findViewById(R.id.blue1);
            blue2 = itemView.findViewById(R.id.blue2);

        }

        public void setMatch(MatchListViewModel matchViewModel){
            this.matchViewModel = matchViewModel;

            matchNumber.setText(String.valueOf(matchViewModel.getMatchNumber()));
            red1.setText("1: " + matchViewModel.getRed1());
            red2.setText("2: " + matchViewModel.getRed2());
            blue1.setText("1: " + matchViewModel.getBlue1());
            blue2.setText("2: " + matchViewModel.getBlue2());


        }

        public void setDisplayText(String displayText){
            matchNumber.setText(displayText);
        }


    }

    public class MatchListAdapter extends RecyclerView.Adapter<MatchViewHolder>{
        private final LayoutInflater inflater;
        private List<MatchListViewModel> matchViewModels;

        MatchListAdapter(Context context){
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.list_item_match, parent, false);
            return new MatchViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
            if(matchViewModels != null){
                holder.setMatch(matchViewModels.get(position));
            }else{
                holder.setDisplayText("No Match Data Yet...");
            }
        }

        void setMatches(List<Match> matches){
            matches = matches.stream()
                    .sorted(Comparator.comparingInt(m -> Integer.parseInt(m.getMatchName().substring(6))))
                    .collect(Collectors.toList());

            matchViewModels = new ArrayList<>();
            for(Match match : matches){
                MatchListViewModel viewModel = new MatchListViewModel(match);
                matchViewModels.add(viewModel);
            }

            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return matchViewModels == null ? 0 : matchViewModels.size();
        }
    }
}
