package org.hartlandrobotics.echelon2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.entities.Evt;
import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelon2.database.entities.EvtTeamCrossRef;
//import org.hartlandrobotics.echelon2.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelon2.database.entities.EvtWithTeams;
import org.hartlandrobotics.echelon2.database.repositories.EventRepo;

import java.util.List;

public class EvtViewModel extends AndroidViewModel {
   private EventRepo mEventRepo;
   private LiveData<List<Evt>> mAllEvents;
   private LiveData<EvtWithTeams> mEventWithTeams;

   public EvtViewModel(Application application) {
      super( application );
      mEventRepo = new EventRepo( application );
      mAllEvents = mEventRepo.getAllEvents();
   }

   public LiveData<List<Evt>> getAllEvents() {
      mAllEvents = mEventRepo.getAllEvents();
      return mAllEvents;
   }

   private volatile boolean haveTeamsBeenLoaded;

   public LiveData<EvtWithTeams> getTeamsForEvent(String eventKey) {
      if ( !haveTeamsBeenLoaded ) {
         mEventWithTeams = mEventRepo.getTeamsForEvent( eventKey );
      }

      return mEventWithTeams;
   }

   /*public LiveData<EvtWithMatches> getMatchesForEvent(String eventKey) {
      return mEventRepo.getMatchesForEvent( eventKey );
   }*/

   public void upsert(Evt event) {
      mEventRepo.upsert( event );
   }

   public void upsert(EvtTeamCrossRef crossRef) {
      mEventRepo.upsert( crossRef );
   }

   public void upsert(EvtMatchCrossRef crossRef) {
      mEventRepo.upsert( crossRef );
   }
}
