package org.hartlandrobotics.echelon2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.DistrictEvtCrossRef;
import org.hartlandrobotics.echelon2.database.entities.DistrictWithEvents;

@Dao
public abstract class DistrictWithEventsDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   public abstract long insert(DistrictEvtCrossRef crossRef);

   @Update(onConflict = OnConflictStrategy.IGNORE)
   public abstract void update(DistrictEvtCrossRef crossRef);

   public void upsert(DistrictEvtCrossRef crossRef) {
      long id = insert( crossRef );
      if ( id == -1 ) {
         update( crossRef );
      }
   }

   @Transaction
   @Query("SELECT * FROM district WHERE district_key = :districtKey")
   public abstract LiveData<DistrictWithEvents> getDistrictEvents(String districtKey );
}