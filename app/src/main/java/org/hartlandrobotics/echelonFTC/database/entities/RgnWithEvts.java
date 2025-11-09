package org.hartlandrobotics.echelonFTC.database.entities;

import java.util.List;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

public class RgnWithEvts {

    @Embedded
    public Rgn region;

    @Relation(
            parentColumn = "regionCode",
            entityColumn = "eventId",
            associateBy = @Junction(RgnEvtCrossRef.class)
    )

    public List<Evt> events;
}
