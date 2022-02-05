package org.hartlandrobotics.echelon2.TBA.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.hartlandrobotics.echelon2.R;
import org.hartlandrobotics.echelon2.TBA.Api;
import org.hartlandrobotics.echelon2.TBA.ApiInterface;
import org.hartlandrobotics.echelon2.TBA.models.SyncDistrict;
import org.hartlandrobotics.echelon2.TBA.models.SyncTeam;
import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.database.repositories.DistrictRepo;
import org.hartlandrobotics.echelon2.database.repositories.TeamRepo;

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
    Button teamPull;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View fragmentView =  inflater.inflate(R.layout.fragment_teams, container, false);
        teamPull = fragmentView.findViewById(R.id.teamPullButton);
        errorTextDisplay = fragmentView.findViewById(R.id.errorTextDisplay);

        setupTeamPull();

        return fragmentView;
    }
    public void setupTeamPull(){
        teamPull.setOnClickListener((view) -> {
            ApiInterface newApi = Api.getApiClient(getActivity().getApplication());

            try{
                Call<List<SyncTeam>> newCall = newApi.getTeamsByEvent("2022milan");
                newCall.enqueue(new Callback<List<SyncTeam>>() {
                    @Override
                    public void onResponse(Call<List<SyncTeam>> call, Response<List<SyncTeam>> response) {
                        try{
                            if(!response.isSuccessful()){
                                errorTextDisplay.setText("Couldn't pull districts");
                            }
                            else{
                                TeamRepo teamRepo = new TeamRepo(TeamsFragment.this.getActivity().getApplication());
                                List<SyncTeam> syncTeam = response.body();
                                List<Team> teams = syncTeam.stream()
                                        .map(team -> team.toTeam())
                                        .collect(Collectors.toList());

                                teamRepo.upsert(teams);

                                errorTextDisplay.setText("Got districts " + teams.size());
                            }
                        }
                        catch(Exception e){
                            errorTextDisplay.setText("Error " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SyncTeam>> call, Throwable t) {
                        errorTextDisplay.setText("Couldn't pull districts");
                    }
                });
            }
            catch(Exception e){
                errorTextDisplay.setText("Error second catch " + e.getMessage());
            }
        });
    }
}
