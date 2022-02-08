package org.hartlandrobotics.echelon2.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
//import org.hartlandrobotics.echelon2.database.entities.EvtWithMatches;

@Dao
public abstract class EvtWithMatchesDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   public abstract long insert(EvtMatchCrossRef crossRef);

   @Update(onConflict = OnConflictStrategy.IGNORE)
   public abstract void update(EvtMatchCrossRef crossRef);

   public void upsert(EvtMatchCrossRef crossRef) {
      long id = insert( crossRef );
      if ( id == -1 ) {
         update( crossRef );
      }
   }

 /*  @Transaction
   @Query("SELECT * FROM event WHERE event_key = :eventKey")
  // public abstract LiveData<EvtWithMatches> getEventMatches(String eventKey);*/
}
