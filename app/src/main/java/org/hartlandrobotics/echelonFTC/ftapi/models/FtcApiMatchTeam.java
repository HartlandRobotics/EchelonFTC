package org.hartlandrobotics.echelonFTC.ftapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FtcApiMatchTeam {
    @JsonProperty("teamNumber")
    private int teamNumber;

    @JsonProperty("displayTeamNumber")
    private String displayTeamNumber;

    @JsonProperty("station")
    private String station;

    public int getTeamNumber() {
        return teamNumber;
    }
    public String getDisplayTeamNumber() {
        return displayTeamNumber;
    }
    public String getStation() {
        return station;
    }
}