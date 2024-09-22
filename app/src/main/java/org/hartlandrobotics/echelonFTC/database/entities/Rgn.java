package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "rgn")
public class Rgn {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "region_key")
    private String regionKey;

    @ColumnInfo(name = "description")
    private String description;

    public Rgn(@NonNull String regionKey, String description) {
        this.regionKey = regionKey;
        this.description = description;
    }

    @NonNull
    public String getRegionKey() {
        return regionKey;
    }
    public String getDescription() {
        return description;
    }

    public String getAbbreviation() {
        return regionKey;
    }

    public String getDisplayName() {
        return description;
    }


}
