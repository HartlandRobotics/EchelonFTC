package org.hartlandrobotics.echelonFTC.ftcscout;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtcScoutMatchScore {
    @JsonProperty("eventSeason")
    private String season;
    @JsonProperty("id")
    public int matchId;
    @JsonProperty("scores")
    private FtcScoutScores scores;


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
