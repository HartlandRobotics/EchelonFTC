package org.hartlandrobotics.echelon2.models;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import org.hartlandrobotics.echelon2.database.entities.Season;
import org.hartlandrobotics.echelon2.database.repositories.SeasonRepo;
import java.util.List;

public class SeasonViewModel extends AndroidViewModel {
    private SeasonRepo seasonRepo;
    private LiveData<List<Season>> seasons;

    public SeasonViewModel(Application application){
        super(application);
        seasonRepo = new SeasonRepo(application);
    }

    public LiveData<List<Season>> getSeasons(){
        seasons = seasonRepo.getSeasons();
        return seasons;
    }
}
