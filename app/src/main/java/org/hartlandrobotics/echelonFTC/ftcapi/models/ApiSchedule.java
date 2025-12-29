package org.hartlandrobotics.echelonFTC.ftcapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiSchedule {
    @JsonProperty("schedule")
    private List<ApiMatch> matches;

    public List<ApiMatch> getMatches() {
        return matches;
    }

}
