package org.hartlandrobotics.echelon2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.Match;

import java.util.List;

@Dao
public abstract class MatchDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Match match);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Match match);

    @Query("SELECT * FROM match")
    public abstract LiveData<List<Match>> getMatches();

    //@Query("SELECT * FROM match WHERE event_key = :event_key")
    //public abstract LiveData<List<Match>> getMatchesByEvent(String event_key);

    public void upsert(Match match){
        long id = insert(match);
        if(id == -1){
            update(match);
        }
    }

    public void upsert(List<Match> matches){
        for(Match m : matches){
            upsert(m);
        }
    }

}
