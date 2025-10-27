package org.hartlandrobotics.echelonFTC.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelonFTC.database.EchelonDatabase;
import org.hartlandrobotics.echelonFTC.database.dao.RgnDao;
import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

import java.util.List;

public class RegionRepo {
    private RgnDao regionDao;

    public RegionRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        regionDao = db.regionDao();
    }

    public LiveData<List<Rgn>> getRegions(){return regionDao.getRgns();}

    public void upsert(Rgn region){
        EchelonDatabase.databaseWriteExecutor.execute( () -> {
            regionDao.upsert(region);
        });
    }

    public void upsert(List<Rgn> regions){
        for( Rgn d : regions){
            upsert(d);
        }
    }
}
