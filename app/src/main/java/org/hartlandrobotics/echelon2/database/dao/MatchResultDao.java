package org.hartlandrobotics.echelon2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;

import java.util.List;

@Dao
public abstract class MatchResultDao {
    @Insert( onConflict = OnConflictStrategy.IGNORE )
    public abstract long insert( MatchResult matchResult );

    @Update( onConflict = OnConflictStrategy.IGNORE )
    public abstract void update( MatchResult matchResult );

    public void upsert( MatchResult matchResult ){
        long id = insert( matchResult );
        if( id == -1 ){
            update( matchResult );
        }
    }

    @Transaction
    @Query("SELECT * FROM match_result WHERE team_key = :teamKey AND match_key = :matchKey")
    public abstract LiveData<MatchResult> getMatchResultByMatchTeam(String matchKey, String teamKey);

    @Transaction
    @Query("SELECT * from match_result WHERE event_key = :eventKey")
    public abstract LiveData<List<MatchResult>> getMatchResultsByEvent(String eventKey);
}
