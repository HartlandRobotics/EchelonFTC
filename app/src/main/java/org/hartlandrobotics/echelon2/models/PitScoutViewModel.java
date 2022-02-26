package org.hartlandrobotics.echelon2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.repositories.PitScoutRepo;

import java.util.List;
import java.util.UUID;

public class PitScoutViewModel extends AndroidViewModel {
    private final PitScoutRepo pitScoutRepo;

    public PitScoutViewModel( Application app ){
        super(app);

        pitScoutRepo = new PitScoutRepo(app);
    }

    public PitScout getDefault(String eventKey, String currentTeamKey){
        PitScout ps = new PitScout();
        ps.setPitScoutKey(UUID.randomUUID().toString());
        ps.setEventKey(eventKey);
        ps.setTeamKey(currentTeamKey);

        return ps;
    }

    public LiveData<PitScout> getPitScout(String eventKey, String teamKey) {
        return pitScoutRepo.getPitScout(eventKey, teamKey);

    }

    public LiveData<List<PitScout>> getPitScoutByEvent(String eventKey){
        return pitScoutRepo.getPitScoutByEvent(eventKey);
    }
    public void upsert( PitScout pitScout ){
        pitScoutRepo.upsert( pitScout );
    }
}
