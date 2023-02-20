package org.hartlandrobotics.echelon2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "region")
public class District {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "region_key")
    private String regionKey;

    @ColumnInfo(name = "description")
    private String description;

    public District(@NonNull String regionKey, String description) {
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
}
