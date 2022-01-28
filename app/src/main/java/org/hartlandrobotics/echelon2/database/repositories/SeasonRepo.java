package org.hartlandrobotics.echelon2.database.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.EchelonDatabase;
import org.hartlandrobotics.echelon2.database.dao.SeasonDao;
import org.hartlandrobotics.echelon2.database.entities.Season;

import java.util.List;

public class SeasonRepo {
    private SeasonDao seasonDao;

    public SeasonRepo(Application application){
        EchelonDatabase db = EchelonDatabase.getDatabase(application);
        seasonDao = db.seasonDao();
    }

    public LiveData<List<Season>> getSeasons(){return seasonDao.getSeasons();}

}
