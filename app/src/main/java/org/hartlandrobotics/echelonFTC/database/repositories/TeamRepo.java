package org.hartlandrobotics.echelonFTC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFTC.database.EchelonDatabase;
//import org.hartlandrobotics.echelonFTC.database.dao.EvtWithTeamsDao;
import org.hartlandrobotics.echelonFTC.database.dao.TeamDao;
import org.hartlandrobotics.echelonFTC.database.entities.EvtTeamCrossRef;
//import org.hartlandrobotics.echelonFTC.database.entities.EvtWithTeams;
import org.hartlandrobotics.echelonFTC.database.entities.Team;

import java.util.List;

public class TeamRepo {
    private TeamDao teamDao;
    //private EvtWithTeamsDao eventsWithTeamsDao;
    private LiveData<List<Team>> allTeams;

    public TeamRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        teamDao = db.teamDao();

        //eventsWithTeamsDao = db.eventTeamsDao();
    }

    public LiveData<List<Team>> getAllTeams(){
        return teamDao.getTeams();
    }

    public void upsert(Team team){
        EchelonDatabase.databaseWriteExecutor.execute(() ->{
            teamDao.upsert(team);
        });
    }

    //public void upsert(EvtTeamCrossRef crossRefEvent){
    //    EchelonDatabase.databaseWriteExecutor.execute(() -> { eventsWithTeamsDao.upsert(crossRefEvent);});
    //}

    public void upsert(List<Team> teams){
        for(Team team : teams){
            upsert(team);
        }
    }

    //public LiveData<EvtWithTeams> getEventsWithTeams(String currentEvent){
    //    return eventsWithTeamsDao.getEventTeams(currentEvent);
    //}
}
