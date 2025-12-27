/*
package org.hartlandrobotics.echelonFTC.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.EvtMatchCrossRef;
import org.hartlandrobotics.echelonFTC.database.entities.Match;

import java.util.Arrays;
import java.util.Comparator;

public class SyncMatch {
    @JsonProperty("match_key")
    private String matchKey;

    @JsonProperty("tournament_level")
    private int tournamentLevel;
    // todo: determine what the values mean, was qf, ef, qf, sf, and f before

    @JsonProperty("match_name")
    private String matchName;

    @JsonProperty("match_start_time")
    private String matchStartTime;

    @JsonProperty("scheduled_time")
    private String scheduledTime;

    @JsonProperty("participants")
    private Participants[] participants;

    public static class Participants {
        @JsonProperty("station")
        private int station;
        // 11 and 12 are red 1 and 2, 21 and 21 are blue 1 and blue 2

        @JsonProperty("team")
        private Team team;
    }

    public static class Team {
        @JsonProperty("team_key")
        String teamKey;
    }

    public Match toMatch() {
        Arrays.sort(participants, Comparator.comparingInt(o -> o.station));
        Match match = new Match(
                getMatchKey(), getMatchName(),
                getTournamentLevel(), getMatchStartTime(), getScheduledTime(),
                participants[0].team.teamKey,
                participants[1].team.teamKey,
                participants[2].team.teamKey,
                participants[3].team.teamKey
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
    public int getTournamentLevel() {
        return tournamentLevel;
    }

    public String getMatchName() {
        return matchName;
    }

    public String getMatchStartTime() {
        return matchStartTime;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }
}
*/
