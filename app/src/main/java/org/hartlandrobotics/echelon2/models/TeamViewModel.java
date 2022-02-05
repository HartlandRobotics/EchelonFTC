package org.hartlandrobotics.echelon2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.entities.Team;
import org.hartlandrobotics.echelon2.database.repositories.TeamRepo;

import java.util.List;

public class TeamViewModel extends AndroidViewModel {
    private TeamRepo teamRepo;

    public TeamViewModel( Application app ){
        super(app);

        teamRepo = new TeamRepo(app);
    }

    public LiveData<List<Team>> getAllTeams(){
        return teamRepo.getAllTeams();
    }
}
