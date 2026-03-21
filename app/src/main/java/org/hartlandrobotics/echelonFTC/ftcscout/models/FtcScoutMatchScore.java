package org.hartlandrobotics.echelonFTC.ftcscout.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.MatchScore;

public class FtcScoutMatchScore {
    @JsonProperty("eventSeason")
    private int year;
    @JsonProperty("eventCode")
    private String eventCode;
    @JsonProperty("id")
    public int matchId;
    @JsonProperty("tournamentLevel")
    private String tournamentLevel;
    @JsonProperty("hasBeenPlayed")
    private boolean hasBeenPlayed;

    @JsonProperty("scores")
    private FtcScoutScores scores;

    public int getYear() { return year; }
    public String getEventCode() { return eventCode; }
    public int getMatchId() { return matchId; }
    public String getTournamentLevel() { return tournamentLevel; }
    public boolean isHasBeenPlayed() { return hasBeenPlayed; }
    public FtcScoutScores getScores() { return scores; }

    public int getRedTotal(){
        if( scores == null ) return 0;
        if( scores.redAlliance == null ) return 0;

        return scores.redAlliance.totalPoints;
    }
    public int getRedPenalty(){
        if( scores == null ) return 0;
        if( scores.redAlliance == null ) return 0;

        return scores.redAlliance.foulPoints;
    }

    public int getBlueTotal(){
        if( scores == null ) return 0;
        if( scores.blueAlliance == null ) return 0;

        return scores.blueAlliance.totalPoints;
    }
    public int getBluePenalty(){
        if( scores == null ) return 0;
        if( scores.blueAlliance == null ) return 0;

        return scores.blueAlliance.foulPoints;
    }

    public MatchScore toMatchScore(){
        MatchScore matchScore = new MatchScore( getYear(),
                getEventCode(),
                getMatchId(),
                getTournamentLevel(),
                isHasBeenPlayed(),
                getRedTotal(),
                getRedPenalty(),
                getBlueTotal(),
                getBlueTotal()
        );

        return matchScore;
    }

    public static class FtcScoutScores {
        @JsonProperty("red")
        private FtcScoutAllianceScore redAlliance;
        @JsonProperty("blue")
        private FtcScoutAllianceScore blueAlliance;
    }

    public static class FtcScoutAllianceScore{
        @JsonProperty("totalPoints")
        private int totalPoints;
        @JsonProperty("penaltyPointsCommitted")
        private int foulPoints;
    }
}
