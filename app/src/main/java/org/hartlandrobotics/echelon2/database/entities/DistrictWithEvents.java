package org.hartlandrobotics.echelon2.database.entities;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class DistrictWithEvents {
    @Embedded
    public District district;

    @Relation(
            parentColumn = "district_key",
            entityColumn = "event_key",
            associateBy = @Junction(DistrictEvtCrossRef.class)
    )
    public List<Evt> events;
}
