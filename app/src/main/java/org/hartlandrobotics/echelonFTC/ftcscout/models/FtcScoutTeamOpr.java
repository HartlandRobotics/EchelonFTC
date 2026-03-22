package org.hartlandrobotics.echelonFTC.ftcscout.models;

import androidx.room.Query;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.MatchScore;
import org.hartlandrobotics.echelonFTC.database.entities.Opr;

public class FtcScoutTeamOpr {
    @JsonProperty("season")
    private int year;
    @JsonProperty("eventCode")
    private String eventCode;
    @JsonProperty("teamNumber")
    public int teamNumber;

    @JsonProperty("stats")
    public FtcScoutStats stats;

    public int getYear() { return year; }
    public String getEventCode() { return eventCode; }
    public int getTeamNumber() { return teamNumber; }
    public int getTotalPoints(){ return (int)stats.avg.totalPoints; }
    public int getPenaltyPoints(){ return (int)stats.avg.penaltyPoints; }


    public Opr toOpr(){
        Opr opr = new Opr(
                getTeamNumber(),
                getTotalPoints(),
                getPenaltyPoints()
        );

        return opr;
    }


    public static class FtcScoutStats {
        @JsonProperty("avg")
        public FtcScoutAverage avg;
    }

    public static class FtcScoutAverage {
        @JsonProperty("penaltyPointsCommitted")
        public double penaltyPoints;
        @JsonProperty("totalPoints")
        public double totalPoints;
    }

}
