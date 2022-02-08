//package org.hartlandrobotics.echelon2.database.entities;
//
//import androidx.room.Embedded;
//import androidx.room.Junction;
//import androidx.room.Relation;
//
//import java.util.List;
//
//public class EvtWithMatches {
//    @Embedded
//    public Evt event;
//
//    @Relation(
//            parentColumn = "event_key",
//            entityColumn = "match_key",
//            associateBy = @Junction(EvtMatchCrossRef.class)
//    )
//    public List<Match> matches;
//}
