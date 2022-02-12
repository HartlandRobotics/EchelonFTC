package org.hartlandrobotics.echelon2.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.EchelonDatabase;
import org.hartlandrobotics.echelon2.database.dao.PitScoutDao;
import org.hartlandrobotics.echelon2.database.entities.PitScout;

public class PitScoutRepo {
    private PitScoutDao pitScoutDao;

    public PitScoutRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);

        pitScoutDao = db.pitScoutDao();
    }

    public LiveData<PitScout> getPitScout(String eventKey, String teamKey){

        return pitScoutDao.getPitScout(eventKey, teamKey);
    }

    public void upsert(PitScout pitScout){
        EchelonDatabase.databaseWriteExecutor.execute(() -> pitScoutDao.upsert(pitScout));
    }
}
