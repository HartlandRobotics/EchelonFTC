package org.hartlandrobotics.echelonFTC.ftcapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiTeams {
    @JsonProperty("teams")
    private List<ApiTeam> teams;
    @JsonProperty("teamCountTotal")
    private int teamCountTotal;
    @JsonProperty("teamCountPage")
    private int teamCountPage;
    @JsonProperty("pageCurrent")
    private int pageCurrent;
    @JsonProperty("pageTotal")
    private int pageTotal;

    public List<ApiTeam> getTeams() {
        return teams;
    }
    public int getTeamCountTotal() {
        return teamCountTotal;
    }
    public int getTeamCountPage() {
        return teamCountPage;
    }
    public int getPageCurrent() {
        return pageCurrent;
    }
    public int getPageTotal() {
        return pageTotal;
    }

}

