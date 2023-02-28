package org.hartlandrobotics.echelon2.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.Team;

public class SyncTeam {
    @JsonProperty("team")
    private TeamProperty team;

    class TeamProperty {
        @JsonProperty("team_key")
        private String teamKey;

        @JsonProperty("team_number")
        private int teamNumber;

        @JsonProperty("team_name_short")
        private String nickname;

        @JsonProperty("team_name_long")
        private String name;

        @JsonProperty("city")
        private String city;
    }

    public String getTeamKey() {
        return team.teamKey;
    }

    public int getTeamNumber() {
        return team.teamNumber;
    }

    public String getNickname() {
        return team.nickname;
    }

    public String getName() {
        return team.name;
    }

    public String getCity() {
        return team.city;
    }

    public Team toTeam(){
        Team team = new Team(
            getTeamKey(), getTeamNumber(), getNickname(), getName(), getCity());
        return team;
    }
}


