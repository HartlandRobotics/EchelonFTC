package org.hartlandrobotics.echelonFTC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFTC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFTC.database.dao.MatchScoreDao;
import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.entities.MatchScore;

import java.util.List;

public class MatchScoreRepo {
    private MatchScoreDao matchScoreDao;

    public MatchScoreRepo( Application application ){
        EchelonDatabase db = EchelonDatabase.getDatabase( application);
        matchScoreDao = db.matchScoreDao();
    }

    public LiveData<List<MatchScore>> getMatchScoresByYearEvent(String year, String eventCode){
        return matchScoreDao.getMatchScoresByYearEvent(year, eventCode);
    }

    public void upsert(MatchScore matchScore){
        EchelonDatabase.databaseWriteExecutor.execute(() -> matchScoreDao.upsert(matchScore));
    }
    public void upsert(List<MatchScore> matchScores){
        EchelonDatabase.databaseWriteExecutor.execute(() -> matchScoreDao.upsert(matchScores));
    }
}
