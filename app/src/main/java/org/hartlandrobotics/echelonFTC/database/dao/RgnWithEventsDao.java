package org.hartlandrobotics.echelonFTC.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelonFTC.database.entities.RgnEvtCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.RgnWithEvents;

@Dao
public abstract class RgnWithEventsDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   public abstract long insert(RgnEvtCrossRef crossRef);

   @Update(onConflict = OnConflictStrategy.IGNORE)
   public abstract void update(RgnEvtCrossRef crossRef);

   public void upsert(RgnEvtCrossRef crossRef) {
      long id = insert( crossRef );
      if ( id == -1 ) {
         update( crossRef );
      }
   }

   @Transaction
   @Query("SELECT * FROM Rgn WHERE region_key = :regionKey")
   public abstract LiveData<RgnWithEvents> getRgnEvents(String regionKey );
}