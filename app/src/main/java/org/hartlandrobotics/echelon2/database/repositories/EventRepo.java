package org.hartlandrobotics.echelon2.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.EchelonDatabase;
import org.hartlandrobotics.echelon2.database.dao.EvtDao;
import org.hartlandrobotics.echelon2.database.dao.EvtWithMatchesDao;
import org.hartlandrobotics.echelon2.database.dao.EvtWithTeamsDao;
import org.hartlandrobotics.echelon2.database.entities.Evt;
import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelon2.database.entities.EvtTeamCrossRef;
//import org.hartlandrobotics.echelon2.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelon2.database.entities.EvtWithTeams;

import java.util.List;

public class EventRepo {
    private EvtDao mEventDao;
    private EvtWithTeamsDao mEventWithTeamsDao;
    private EvtWithMatchesDao mEventWithMatchesDao;

    private LiveData<List<Evt>> mAllEvents;
    private LiveData<EvtWithTeams> mEventWithTeams;

    public EventRepo(Application application) {
        EchelonDatabase db = EchelonDatabase.getDatabase( application );

        mEventDao = db.eventDao();
        mAllEvents = mEventDao.getEvents();

        mEventWithTeamsDao = db.eventTeamsDao();
        mEventWithMatchesDao = db.eventMatchesDao();
    }

    public LiveData<List<Evt>> getAllEvents() {
        mAllEvents = mEventDao.getEvents();
        return mAllEvents;
    }

    public LiveData<EvtWithTeams> getTeamsForEvent(String eventKey) {
        return mEventWithTeamsDao.getEventTeams( eventKey );
    }

   /* public LiveData<EvtWithMatches> getMatchesForEvent(String eventKey) {
        return mEventWithMatchesDao.getEventMatches( eventKey );
    }*/

    public void upsert(Evt event) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> mEventDao.upsert( event ) );
    }

    public void upsert(EvtTeamCrossRef crossRef) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> {
            mEventWithTeamsDao.upsert(crossRef);
        });
    }

    public void upsert(EvtMatchCrossRef crossRef) {
        EchelonDatabase.databaseWriteExecutor.execute( () -> mEventWithMatchesDao.upsert( crossRef ) );
    }
}
