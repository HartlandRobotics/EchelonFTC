package org.hartlandrobotics.echelonFTC.ftapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.database.entities.Team;

public class FtcApiTeam {
    @JsonProperty("teamNumber")
    private int teamNumber;
    @JsonProperty("displayTeamNumber")
    private String displayTeamNumber;
    @JsonProperty("nameShort")
    private String nameShort;

    public int getTeamNumber() {
        return teamNumber;
    }
    public String getDisplayTeamNumber() {
        return displayTeamNumber;
    }
    public String getNameShort() {
        return nameShort;
    }

    public Team toTeam(){
        return new Team(
                getDisplayTeamNumber(),
                getTeamNumber(),
                getNameShort(),
                getNameShort(),
                StringUtils.EMPTY
        );
    }
}