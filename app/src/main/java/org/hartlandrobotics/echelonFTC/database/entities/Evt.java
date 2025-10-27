package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="event")
public class Evt {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="eventId")
    public String eventId;

    @ColumnInfo(name = "code")
    public String code;

    @ColumnInfo(name = "regionCode")
    public String regionCode;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "remote")
    public boolean remote;

    @ColumnInfo(name = "hybrid")
    public boolean hybrid;

    public Evt(@NonNull String eventId, String code, String regionCode, String name, boolean remote, boolean hybrid) {
        this.eventId = eventId;
        this.code = code;
        this.regionCode = regionCode;
        this.name = name;
        this.remote = remote;
        this.hybrid = hybrid;
    }

    //public RgnEvtCrossRef toRgnEvent( String regionCode ){
    //    return new RgnEvtCrossRef(regionCode,getCode());
    //}

    @NonNull
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getRegionCode() { return regionCode; }
    public void setRegionCode(String regionCode) { this.regionCode = regionCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isRemote() { return remote; }
    public void setRemote(boolean remote) { this.remote = remote; }

    public boolean isHybrid() { return hybrid;}
    public void setHybrid(boolean hybrid) { this.hybrid = hybrid; }

}
