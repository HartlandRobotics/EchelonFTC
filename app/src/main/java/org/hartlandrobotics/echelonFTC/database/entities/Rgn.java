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

    public Rgn(@NonNull String regionCode) {
        this.regionCode = regionCode;
    }

    @NonNull
    public String getRegionCode() {
        return regionCode;
    }
}
