package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "season")
public class Season {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "year")
    private int year;

    public Season(@NonNull String name, int year) {
        this.name = name;
        this.year = year;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }
}
