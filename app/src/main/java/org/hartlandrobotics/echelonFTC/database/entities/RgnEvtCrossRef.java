package org.hartlandrobotics.echelonFTC.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "region_event",
        primaryKeys = {"region_key", "event_key"},
        indices = {
                     @Index(value = {"region_key", "event_key"}),
                     @Index(value = {"event_key", "region_key"})
         })

public class RgnEvtCrossRef {
   public RgnEvtCrossRef(String regionKey, String eventKey){
      this.regionKey = regionKey;
      this.eventKey = eventKey;
   }

   @NonNull
   @ColumnInfo(name = "region_key")
   public String regionKey;

   @NonNull
   @ColumnInfo(name = "event_key")
   public String eventKey;
}
