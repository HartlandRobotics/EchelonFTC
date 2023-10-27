package org.hartlandrobotics.echelon2.database.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class RgnWithEvents {
    @Embedded
    public Rgn district;

    @Relation(
            parentColumn = "region_key",
            entityColumn = "event_key",
            associateBy = @Junction(RgnEvtCrossRef.class)
    )
    public List<Evt> events;
}
