package org.hartlandrobotics.echelonFTC.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

import java.util.List;

@Dao
public abstract class RgnDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Rgn district);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Rgn district);

    @Query("SELECT * FROM Rgn")
    public abstract LiveData<List<Rgn>> getRgns();

    public void upsert(Rgn district){
        long id = insert(district);
        if(id == -1){
            update(district);
        }
    }

    public void upsert(List<Rgn> districts){
        for( Rgn d : districts ){
            upsert(d);
        }
    }
}
