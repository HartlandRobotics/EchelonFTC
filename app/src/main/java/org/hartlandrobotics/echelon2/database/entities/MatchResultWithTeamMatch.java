package org.hartlandrobotics.echelon2.database.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
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
