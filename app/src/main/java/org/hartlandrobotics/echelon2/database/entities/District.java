package org.hartlandrobotics.echelon2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "district")
public class District {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "district_key")
    private String districtKey;

    @ColumnInfo(name = "abbreviation")
    private String abbreviation;

    @ColumnInfo(name = "display_name")
    private String displayName;

    @ColumnInfo(name = "year")
    private int year;

    public District(@NonNull String districtKey, String abbreviation, String displayName, int year) {
        this.districtKey = districtKey;
        this.abbreviation = abbreviation;
        this.displayName = displayName;
        this.year = year;
    }

    @NonNull
    public String getDistrictKey() {
        return districtKey;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getYear() {
        return year;
    }
}
