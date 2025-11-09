package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rgn")
public class Rgn {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "regionCode")
    private String regionCode;

    @ColumnInfo(name = "name")
    public String name;

    public Rgn(@NonNull String regionCode, String name) {
        this.regionCode = regionCode;
        this.name = name;
    }

    @NonNull
    public String getRegionCode() {
        return regionCode;
    }

    public String getName(){ return name; }
}
