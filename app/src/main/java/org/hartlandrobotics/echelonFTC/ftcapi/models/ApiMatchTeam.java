package org.hartlandrobotics.echelonFTC.ftcapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiMatchTeam {
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