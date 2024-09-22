package org.hartlandrobotics.echelonFTC.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import org.hartlandrobotics.echelonFTC.database.entities.EvtTeamCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.EvtWithTeams;

@Dao
public abstract class EvtWithTeamsDao {
   @Insert( onConflict = OnConflictStrategy.REPLACE )
   public abstract long insert( EvtTeamCrossRef crossRef );

   @Update( onConflict = OnConflictStrategy.REPLACE )
   public abstract void update( EvtTeamCrossRef crossRef );

   public void upsert( EvtTeamCrossRef crossRef ){
      long id = insert( crossRef );
      if( id == -1 ) {
         update(crossRef);
      }
   }

   @Transaction
   @Query("SELECT * FROM event WHERE event_key = :eventKey")
   public abstract LiveData<EvtWithTeams> getEventTeams(String eventKey);
}