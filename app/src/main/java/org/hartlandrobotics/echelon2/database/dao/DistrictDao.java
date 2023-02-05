package org.hartlandrobotics.echelon2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.Region;

import java.util.List;

@Dao
public abstract class DistrictDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Region region);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    public abstract void update(Region region);

    @Query("SELECT * FROM Region")
    public abstract LiveData<List<Region>> getDistricts();

    @Query("SELECT * FROM Region WHERE year = :year")
    public abstract LiveData<List<Region>> getDistrictsByYear(int year);

    public void upsert(Region region){
        long id = insert(region);
        if(id == -1){
            update(region);
        }
    }

    public void upsert(List<Region> regions){
        for( Region d : regions){
            upsert(d);
        }
    }
}
