package org.hartlandrobotics.echelonFTC.ftapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FtcApiSchedule {
    @JsonProperty("schedule")
    private List<FtcApiMatch> matches;

    public List<FtcApiMatch> getMatches() {
        return matches;
    }

}
