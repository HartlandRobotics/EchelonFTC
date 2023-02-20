package org.hartlandrobotics.echelon2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.District;

import java.util.List;

@Dao
public abstract class DistrictDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(District district);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(District district);

    @Query("SELECT * FROM region")
    public abstract LiveData<List<District>> getDistricts();

    //@Query("SELECT * FROM region WHERE year = :year")
    //public abstract LiveData<List<District>> getDistrictsByYear(int year);

    public void upsert(District district){
        long id = insert(district);
        if(id == -1){
            update(district);
        }
    }

    public void upsert(List<District> districts){
        for( District d : districts ){
            upsert(d);
        }
    }
}
