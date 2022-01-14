package org.hartlandrobotics.echelon2.database.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.Evt;

import java.util.List;

@Dao
public abstract class EvtDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract  long insert(Evt event);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Evt event);

    @Query("SELECT * FROM event")
    public abstract LiveData<List<Evt>> getEvents();

    public void upsert(Evt event){
        long id = insert(event);
        if(id == -1){
            update(event);
        }
    }
}
