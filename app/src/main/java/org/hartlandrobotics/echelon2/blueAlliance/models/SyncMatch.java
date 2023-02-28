package org.hartlandrobotics.echelon2.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelon2.database.entities.Match;

import java.util.ArrayList;

public class SyncMatch {
    @JsonProperty("match_key")
    private String matchKey;

    @JsonProperty("comp_level")
    private String compLevel;
    // possible values [ qm, ef, qf, sf, f ]
    // need to revisit later. in the orange alliance this is an integer.

    @JsonProperty("play_number")
    private int playNumber;
    //play_number?

    @JsonProperty("winning_alliance")
    private String winningAlliance;
    // not in doc. it seems there is a hierarchy structure that makes it complicated to find what this is.

    @JsonProperty("match_start_time")
    private String marchStartTime;


    @JsonProperty("prestart_tiem")
    private String prestartTime;

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
