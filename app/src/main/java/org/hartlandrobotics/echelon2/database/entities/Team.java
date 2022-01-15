package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "team")
public class Team {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "team_number")
    private int teamNumber;

    @ColumnInfo(name = "nickname")
    private String nickname;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "school_name")
    private String schoolName;

    public Team(@NonNull String teamKey, int teamNumber, String nickname, String name, String schoolName) {
        this.teamKey = teamKey;
        this.teamNumber = teamNumber;
        this.nickname = nickname;
        this.name = name;
        this.schoolName = schoolName;
    }

    @NonNull
    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(@NonNull String teamKey) {
        this.teamKey = teamKey;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
