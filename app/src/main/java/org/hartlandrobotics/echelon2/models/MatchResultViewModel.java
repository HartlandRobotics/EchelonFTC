package org.hartlandrobotics.echelon2.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelon2.database.dao.MatchResultDao;
import org.hartlandrobotics.echelon2.database.entities.MatchResult;
import org.hartlandrobotics.echelon2.database.entities.PitScout;
import org.hartlandrobotics.echelon2.database.repositories.MatchResultRepo;

import java.util.List;

public class MatchResultViewModel extends AndroidViewModel {
    private MatchResultRepo matchResultRepo;

    public MatchResultViewModel( Application app){
        super(app);

        matchResultRepo = new MatchResultRepo(app);
    }

    public MatchResult getDefault(String eventKey, String matchKey, String teamKey){
        MatchResult matchResult = new MatchResult(
                StringUtils.EMPTY,
                eventKey,
                matchKey,
                teamKey,
        false,
        false,
        0,
        0,
        0,
        0,
        0,
        false,
        false,
        false,
        false
        );

        return matchResult;
    }

    public LiveData<MatchResult> getMatchResultByMatchTeam(String matchKey, String teamKey ){
        return matchResultRepo.getMatchResultByMatchTeam(matchKey, teamKey);
    }

    public LiveData<List<MatchResult>> getMatchResultsByEvent(String eventKey){
        return matchResultRepo.getMatchResultsByEvent(eventKey);

    }

    public void upsert( MatchResult matchResult ){
        matchResultRepo.upsert( matchResult );
    }
}
