package org.hartlandrobotics.echelonFTC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFTC.database.EchelonDatabase;
//import org.hartlandrobotics.echelonFTC.database.dao.RgnWithEventsDao;
import org.hartlandrobotics.echelonFTC.database.dao.EvtDao;
import org.hartlandrobotics.echelonFTC.database.dao.EvtWithMatchesDao;
//import org.hartlandrobotics.echelonFTC.database.dao.EvtWithTeamsDao;
//import org.hartlandrobotics.echelonFTC.database.entities.RgnEvtCrossRef;
//import org.hartlandrobotics.echelonFTC.database.entities.RgnWithEvents;
import org.hartlandrobotics.echelonFTC.database.entities.Evt;
import org.hartlandrobotics.echelonFTC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.EvtTeamCrossRef;
//import org.hartlandrobotics.echelonFTC.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelonFTC.database.entities.EvtWithMatches;
//import org.hartlandrobotics.echelonFTC.database.entities.EvtWithTeams;

import java.util.List;

public class EventRepo {
    private EvtDao eventDao;
    //private EvtWithTeamsDao eventWithTeamsDao;
    private EvtWithMatchesDao eventWithMatchesDao;
//    private RgnWithEventsDao rgnWithEventDao;


    public EventRepo(Application application) {
        EchelonDatabase db = EchelonDatabase.getDatabase( application );

        eventDao = db.eventDao();

        //eventWithTeamsDao = db.eventTeamsDao();
        //eventWithMatchesDao = db.eventMatchesDao();
//        rgnWithEventDao = db.districtEventsDao();
    }

    //public LiveData<EvtWithTeams> getEventWithTeams(String eventKey) {
    //   return eventWithTeamsDao.getEventTeams( eventKey );
    //}

    public LiveData<EvtWithMatches> getEventWithMatchs(String eventKey){
        return eventWithMatchesDao.getEventMatches(eventKey);
    }

    public void upsert(Evt event) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> eventDao.upsert( event ) );
    }

    //public void upsert(RgnEvtCrossRef crossRefDistrict){
    //    EchelonDatabase.databaseWriteExecutor.execute( () -> { rgnWithEventDao.upsert(crossRefDistrict);
    //    });
    //}

    //public void upsert(EvtTeamCrossRef crossRef) {
    //    EchelonDatabase.databaseWriteExecutor.execute( () -> {
    //        eventWithTeamsDao.upsert(crossRef);
    //    });
    //}

    public void upsert(EvtMatchCrossRef crossRef) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> eventWithMatchesDao.upsert( crossRef ) );
    }

    public void upsert(List<Evt> events){
        for(Evt evt: events){
            upsert(evt);
        }
    }

    public LiveData<List<Evt>> getEvent(String eventKey){
        return eventDao.getEvent(eventKey);
    }

    //public LiveData<RgnWithEvents> getDistrictWithEvents(String currentDistrict) {
    //    return rgnWithEventDao.getRgnEvents(currentDistrict);
    //}

    public LiveData<EvtWithMatches> getMatchesForEvent(String eventKey){
        return eventWithMatchesDao.getEventMatches(eventKey);
    }
}
