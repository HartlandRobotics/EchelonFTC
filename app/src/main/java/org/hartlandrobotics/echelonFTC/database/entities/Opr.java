package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "opr" )

public class Opr {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "team_number")
    private int teamNumber;
    //teamkey

    @ColumnInfo(name = "opr")
    private double opr;
    //opr

    @ColumnInfo(name = "foul")
    private double foul;

    public Opr(int teamNumber, double opr, double foul) {
        this.teamNumber = teamNumber;
        this.opr = opr;
        this.foul = foul;
    }

    public int getTeamNumber() { return teamNumber; }

    public double getOpr() { return opr; }

    public double getFoul() { return foul; }

}
