package org.hartlandrobotics.echelon2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "event_match",
        primaryKeys = {"event_key", "match_key"},
        indices = {@Index(value = {"event_key", "match_key"}),
                @Index(value = {"match_key", "event_key"})
        }
)
public class EvtMatchCrossRef {
   public EvtMatchCrossRef(String eventKey, String matchKey) {
      this.eventKey = eventKey;
      this.matchKey = matchKey;
   }

   @NonNull
   @ColumnInfo(name = "event_key")
   public String eventKey;

   @NonNull
   @ColumnInfo(name = "match_key")
   public String matchKey;
}
