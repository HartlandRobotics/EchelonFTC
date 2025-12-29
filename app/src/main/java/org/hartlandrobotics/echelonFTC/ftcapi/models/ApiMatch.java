package org.hartlandrobotics.echelonFTC.ftcapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.database.entities.Match;

import java.util.List;

public class ApiMatch {
    @JsonProperty("description")
    private String description;

    @JsonProperty("matchNumber")
    private int matchNumber;

    @JsonProperty("teams")
    private List<ApiMatchTeam> teams;

    public String getDescription() {
        return description;
    }
    public int getMatchNumber() {
        return matchNumber;
    }

    public List<ApiMatchTeam> getTeams() {
        return teams;
    }


    public Match toMatch(String year, String event) {
        List<ApiMatchTeam> teams = getTeams();
        Match match = new Match(
                year + "_" + event + "_" + getMatchNumber(),
                String.valueOf(getMatchNumber()),
                1,
                StringUtils.EMPTY,
                StringUtils.EMPTY,
                teams.stream().filter(team -> team.getStation().equals("Red1")).findFirst().get().getDisplayTeamNumber(),
                teams.stream().filter(team -> team.getStation().equals("Red2")).findFirst().get().getDisplayTeamNumber(),
                teams.stream().filter(team -> team.getStation().equals("Blue1")).findFirst().get().getDisplayTeamNumber(),
                teams.stream().filter(team -> team.getStation().equals("Blue2")).findFirst().get().getDisplayTeamNumber()
        );
        return match;
    }

}

