package org.hartlandrobotics.echelon2.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.EchelonDatabase;
import org.hartlandrobotics.echelon2.database.dao.EvtWithMatchesDao;
import org.hartlandrobotics.echelon2.database.dao.MatchDao;
import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelon2.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelon2.database.entities.Match;

import java.util.List;

public class MatchRepo {
    private EvtWithMatchesDao eventMatchDao;
    private MatchDao matchDao;

    public MatchRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        eventMatchDao = db.eventMatchesDao();
        matchDao = db.matchDao();
    }

    public LiveData<EvtWithMatches> getMatchesByEvent(String eventKey){return eventMatchDao.getEventMatches(eventKey);}

    public void upsert(EvtMatchCrossRef match){
        EchelonDatabase.databaseWriteExecutor.execute(() -> {
            eventMatchDao.upsert(match);
        });
    }

    public void upsert(Match match){
        EchelonDatabase.databaseWriteExecutor.execute(()-> matchDao.upsert(match));
    }

    public void upsert(List<Match> matches){
        for(Match m : matches){
            upsert(m);
        }
    }
}
