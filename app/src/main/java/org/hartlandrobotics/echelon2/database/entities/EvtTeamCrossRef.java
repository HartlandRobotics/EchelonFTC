package org.hartlandrobotics.echelon2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity( tableName = "event_team",
        primaryKeys = {"event_key", "team_key"},
        indices = { @Index( value = {"event_key", "team_key"} ),
                @Index( value = {"team_key", "event_key"} )
        }
)
public class EvtTeamCrossRef {
   public EvtTeamCrossRef(String eventKey, String teamKey ){
      this.eventKey = eventKey;
      this.teamKey = teamKey;
   }

   @NonNull
   @ColumnInfo( name = "event_key")
   public String eventKey;

   @NonNull
   @ColumnInfo( name = "team_key")
   public String teamKey;
}
