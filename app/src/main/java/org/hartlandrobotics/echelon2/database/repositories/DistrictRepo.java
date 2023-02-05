package org.hartlandrobotics.echelon2.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.EchelonDatabase;
import org.hartlandrobotics.echelon2.database.dao.DistrictDao;
import org.hartlandrobotics.echelon2.database.entities.Region;

import java.util.List;

public class DistrictRepo {
    private DistrictDao districtDao;

    public DistrictRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        districtDao = db.districtDao();

    }

    public LiveData<List<Region>> getDistrictsByYear(int year){return districtDao.getDistrictsByYear(year);}

    public void upsert(Region region){
        EchelonDatabase.databaseWriteExecutor.execute( () -> {
            districtDao.upsert(region);
        });
    }

    public void upsert(List<Region> regions){
        for( Region d : regions){
            upsert(d);
        }
    }
}
