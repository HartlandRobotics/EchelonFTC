package org.hartlandrobotics.echelon2.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.PitScout;

@Dao
public abstract class PitScoutDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(PitScout pitScout);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(PitScout pitScout);

    public void upsert(PitScout pitScout){
        long id = insert(pitScout);
        if(id == -1){
            update(pitScout);
        }
    }

    @Transaction
    @Query("SELECT * FROM pit_scout")
    public abstract LiveData<PitScout> getPitScout();

}