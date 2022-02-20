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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.models.SyncTeam;
import org.hartlandrobotics.echelon2.database.entities.EvtTeamCrossRef;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.database.repositories.TeamRepo;
import org.hartlandrobotics.echelon2.models.TeamViewModel;
import org.hartlandrobotics.echelon2.status.BlueAllianceStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsFragment extends Fragment {
    public TeamsFragment(){
        // required empty constructor
    }
    // pulling teams by event

    @Override
    public void onCreate(Bundle savedInstanceState){super.onCreate(savedInstanceState);}

    private TextView errorTextDisplay;
    private Button teamPull;
    private RecyclerView teamRecycler;
    private TeamListAdapter teamListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_teams, container, false);
        teamPull = fragmentView.findViewById(R.id.teamPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);

        return fragmentView;
    }

    @Override
    public void onResume(){
        super.onResume();
        setupCurrentTeams();
        setupTeamPull();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        teamListAdapter = new TeamListAdapter(getActivity());

        teamRecycler = view.findViewById(R.id.teamRecyclerView);
        teamRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        teamRecycler.setAdapter(teamListAdapter);
        teamRecycler.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
    }

    public void setupCurrentTeams(){
        Context appContext = getActivity().getApplicationContext();
        BlueAllianceStatus status = new BlueAllianceStatus(appContext);
        String eventKey = status.getEventKey();

        TeamRepo teamRepo = new TeamRepo(TeamsFragment.this.getActivity().getApplication());
        teamRepo.getEventsWithTeams(eventKey).observe(getViewLifecycleOwner(), event -> {
            teamListAdapter.setTeams(event.teams);
        });
    }

    public void setupTeamPull(){
        teamPull.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Context context = getActivity().getApplication();
                BlueAllianceStatus status = new BlueAllianceStatus(context);
                String eventKey = status.getEventKey();

                Call<List<SyncTeam>> newCall = newApi.getTeamsByEvent(eventKey);
                newCall.enqueue(new Callback<List<SyncTeam>>() {
                    @Override
                    public void onResponse(Call<List<SyncTeam>> call, Response<List<SyncTeam>> response) {
                        try{
                            if(!response.isSuccessful()){
                                errorTextDisplay.setText("Couldn't pull Teams");
                            }
                            else{
                                TeamRepo teamRepo = new TeamRepo(TeamsFragment.this.getActivity().getApplication());
                                List<SyncTeam> syncTeam = response.body();
                                List<Team> teams = syncTeam.stream()
                                        .map(team -> team.toTeam())
                                        .collect(Collectors.toList());

                                teamRepo.upsert(teams);

                                for(Team team: teams){
                                    EvtTeamCrossRef crossRef = team.toEventTeam(eventKey);
                                    teamRepo.upsert(crossRef);
                                }

                                teamListAdapter.setTeams(teams);

                                errorTextDisplay.setText("Got Teams " + teams.size());
                            }
                        }
                        catch(Exception e){
                            errorTextDisplay.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncTeam>> call, Throwable t) {
                        errorTextDisplay.setText("Couldn't pull teams");
                    }
                });
            }
            catch(Exception e){
                errorTextDisplay.setText("Error second catch " + e.getMessage());
            }
        });
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView teamNameText;
        private final TextView teamDesc;

        private Team team;

        private TeamViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener( this );

            teamNameText = itemView.findViewById( R.id.teamName );
            teamDesc = itemView.findViewById( R.id.teamDescription );
        }

        public void setTeam(Team team ){
            this.team = team;

            teamNameText.setText(team.getNickname());
            teamDesc.setText(team.getTeamKey());
        }

        public void setDisplayText( String displayText ){
            teamNameText.setText(displayText);
        }

        @Override
        public void onClick(View view) {

        }

    }

    public class TeamListAdapter extends RecyclerView.Adapter<TeamViewHolder> {


        private final LayoutInflater inflater;
        private List<Team> teams; // cached copy of districts

        TeamListAdapter(Context context ){ inflater = LayoutInflater.from(context); }

        @NonNull
        @Override
        public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = inflater.inflate( R.layout.list_item_team, parent, false );
            return new TeamViewHolder( itemView );
        }

        @Override
        public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
            if( teams != null ){
                holder.setTeam( teams.get(position) );
            } else {
                holder.setDisplayText("No Team Data Yet...");
            }
        }

        void setTeams(List<Team> teamsPara){
            teams = new ArrayList<>();
            for(Team team: teamsPara){
                teams.add(team);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if( teams != null ) return teams.size();
            return 0;
        }
    }
}
