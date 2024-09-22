package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

public class MatchResultWithTeamMatch {
    @Embedded
    public MatchResult matchResult;

    @Relation(
            parentColumn =  "team_key",
            entityColumn = "team_key"
    )
    public Team team;

    @Relation(
            parentColumn = "match_key",
            entityColumn = "match_key"
    )
    public Match match;

}
