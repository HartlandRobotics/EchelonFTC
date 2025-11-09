package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(tableName = "region_event",
        primaryKeys = {"regionCode", "eventId"},
        indices = {
                @Index(value = {"regionCode", "eventId"}),
                @Index(value = {"eventId", "regionCode"})
        })

public class RgnEvtCrossRef {
    public RgnEvtCrossRef(String regionCode, String eventId){
        this.regionCode = regionCode;
        this.eventId = eventId;
    }

    @NonNull
    @ColumnInfo(name = "regionCode")
    public String regionCode;

    @NonNull
    @ColumnInfo(name = "eventId")
    public String eventId;
}
