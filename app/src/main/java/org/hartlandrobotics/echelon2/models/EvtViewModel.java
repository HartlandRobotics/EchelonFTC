package org.hartlandrobotics.echelon2.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.hartlandrobotics.echelon2.database.entities.DistrictEvtCrossRef;
import org.hartlandrobotics.echelon2.database.entities.Evt;
import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelon2.database.entities.EvtTeamCrossRef;
//import org.hartlandrobotics.echelon2.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelon2.database.entities.EvtWithMatches;
import org.hartlandrobotics.echelon2.database.entities.EvtWithTeams;
import org.hartlandrobotics.echelon2.database.repositories.EventRepo;

import java.util.List;

public class EvtViewModel extends AndroidViewModel {
   private EventRepo mEventRepo;

   public EvtViewModel(Application application) {
      super( application );
      mEventRepo = new EventRepo( application );
   }

   private volatile boolean haveTeamsBeenLoaded;

   public LiveData<EvtWithTeams> getEventWithTeams(String eventKey) {
      return mEventRepo.getEventWithTeams( eventKey );
   }

   public LiveData<EvtWithMatches> getEventWithMatchs(String eventKey) {
      return mEventRepo.getMatchesForEvent( eventKey );
   }

   public void upsert(Evt event) {
      mEventRepo.upsert( event );
   }

   public void upsert(DistrictEvtCrossRef crossRef){ mEventRepo.upsert(crossRef);}

   public void upsert(EvtTeamCrossRef crossRef) {
      mEventRepo.upsert( crossRef );
   }

   public void upsert(EvtMatchCrossRef crossRef) {
      mEventRepo.upsert( crossRef );
   }
}
