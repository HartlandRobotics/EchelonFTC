package org.hartlandrobotics.echelonFTC.ftapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FtcApiTeams {
    @JsonProperty("teams")
    private List<FtcApiTeam> teams;
    @JsonProperty("teamCountTotal")
    private int teamCountTotal;
    @JsonProperty("teamCountPage")
    private int teamCountPage;
    @JsonProperty("pageCurrent")
    private int pageCurrent;
    @JsonProperty("pageTotal")
    private int pageTotal;

    public List<FtcApiTeam> getTeams() {
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

