package org.hartlandrobotics.echelon2.database.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class EvtWithTeams {

   @Embedded
   public Evt event;

   @Relation(
           parentColumn = "event_key",
           entityColumn = "team_key",
           associateBy = @Junction(EvtTeamCrossRef.class)
   )
   public List<Team> teams;
}
