package org.hartlandrobotics.echelon2.TBA.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelon2.database.entities.Match;

import java.util.ArrayList;

public class SyncMatch {
    @JsonProperty("key")
    private String matchKey;
    //yyyy[EVENT_CODE]_[COMP_LEVEL]m[MATCH_NUMBER]

    @JsonProperty("comp_level")
    private String compLevel;
    // possible values [ qm, ef, qf, sf, f ]

    @JsonProperty("match_number")
    private int matchNumber;

    @JsonProperty("winning_alliance")
    private String winningAlliance;

    @JsonProperty("time")
    private int time;

    @JsonProperty("predicted_time")
    private int predictedTime;

    @JsonProperty("alliances")
    private Alliances alliances;

    public static class Alliances {
        @JsonProperty("blue")
        private MatchAlliance blueAlliance;

        @JsonProperty("red")
        private MatchAlliance redAlliance;
    }

    public static class MatchAlliance {
        @JsonProperty("team_keys")
        ArrayList<String> teamKeys;
    }

    public Match toMatch() {
        Match match = new Match(
                getMatchKey(), getMatchNumber(),
                getCompLevel(), getTime(), getPredictedTime(),
                alliances.redAlliance.teamKeys.get( 0 ),
                alliances.redAlliance.teamKeys.get( 1 ),
                alliances.redAlliance.teamKeys.get( 2 ),
                alliances.blueAlliance.teamKeys.get( 0 ),
                alliances.blueAlliance.teamKeys.get( 1 ),
                alliances.blueAlliance.teamKeys.get( 2 )
        );
        return match;
    }

    public EvtMatchCrossRef toEvtMatch(String eventKey) {
        EvtMatchCrossRef crossRef = new EvtMatchCrossRef( eventKey, getMatchKey() );
        return crossRef;
    }

    //yyyy[EVENT_CODE]_[COMP_LEVEL]m[MATCH_NUMBER]
    public String getMatchKey() {
        return matchKey;
    }

    // possible values [ qm, ef, qf, sf, f ]
    public String getCompLevel() {
        return compLevel;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public int getTime() {
        return time;
    }

    public int getPredictedTime() {
        return predictedTime;
    }
}
