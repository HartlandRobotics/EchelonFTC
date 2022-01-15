package org.hartlandrobotics.echelon2.TBA.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SyncTeam {
    @JsonProperty("key")
    private String teamKey;

    @JsonProperty("team_number")
    private int teamNumber;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("name")
    private String name;

    @JsonProperty("school_name")
    private String schoolName;


    public String getTeamKey() {
        return teamKey;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }

    public String getSchoolName() {
        return schoolName;
    }
    

}
