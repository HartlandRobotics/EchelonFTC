package org.hartlandrobotics.echelonFTC.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelonFTC.database.entities.Opr;

import java.util.List;

@Dao
public abstract class OprDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Opr opr);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Opr opr);

    //@Query("SELECT * FROM match_score WHERE year = :year AND event_code = :eventCode")

    @Transaction
    @Query("SELECT * FROM opr")
    public abstract LiveData<List<Opr>> getOprs();

    public void upsert(Opr opr){
        long id = insert(opr);
        if(id == -1){
            update(opr);
        }
    }

    public void upsert(List<Opr> oprs){
        for( Opr o : oprs ){
            upsert(o);
        }
    }
}
