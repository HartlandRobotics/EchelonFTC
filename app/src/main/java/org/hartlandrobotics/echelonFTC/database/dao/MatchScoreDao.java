package org.hartlandrobotics.echelonFTC.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;
import org.hartlandrobotics.echelonFTC.database.entities.MatchScore;
import java.util.List;

@Dao
public abstract class MatchScoreDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(MatchScore match);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(MatchScore match);

    @Transaction
    @Query("SELECT * FROM match_score WHERE year = :year AND event_code = :eventCode")
    public abstract LiveData<List<MatchScore>> getMatchScoresByYearEvent(String year, String eventCode);


    public void upsert(MatchScore matchScore){
        long id = insert(matchScore);
        if(id == -1){
            update(matchScore);
        }
    }

    public void upsert(List<MatchScore> matchScores){
        for(MatchScore ms : matchScores){
            upsert(ms);
        }
    }

}
