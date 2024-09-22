package org.hartlandrobotics.echelonFTC.database.repositories;

import android.app.Application;

import org.hartlandrobotics.echelonFTC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFTC.database.dao.EvtWithMatchesDao;
import org.hartlandrobotics.echelonFTC.database.dao.MatchDao;
import org.hartlandrobotics.echelonFTC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.Match;

import java.util.List;

public class MatchRepo {
    private EvtWithMatchesDao eventMatchDao;
    private MatchDao matchDao;

    public MatchRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        eventMatchDao = db.eventMatchesDao();
        matchDao = db.matchDao();
    }


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
