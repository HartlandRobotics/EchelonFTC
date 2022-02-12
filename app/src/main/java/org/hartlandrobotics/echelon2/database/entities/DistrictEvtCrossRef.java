package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "district_event",
        primaryKeys = {"district_key", "event_key"},
        indices = {
                     @Index(value = {"district_key", "event_key"}),
                     @Index(value = {"event_key", "district_key"})
         })

public class DistrictEvtCrossRef {
   public DistrictEvtCrossRef(String districtKey, String eventKey){
      this.districtKey = districtKey;
      this.eventKey = eventKey;
   }

   @NonNull
   @ColumnInfo(name = "district_key")
   public String districtKey;

   @NonNull
   @ColumnInfo(name = "event_key")
   public String eventKey;
}
