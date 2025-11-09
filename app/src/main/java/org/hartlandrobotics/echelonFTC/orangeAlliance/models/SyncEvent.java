package org.hartlandrobotics.echelonFTC.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.Evt;
import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

import java.util.List;

public class SyncEvent {
    @JsonProperty("Events")
    public List<EventProperty> events;


    public static class EventProperty {
        @JsonProperty("eventId")
        private String eventId;
        @JsonProperty("code")
        private String code;
        @JsonProperty("regionCode")
        private String regionCode;
        @JsonProperty("name")
        private String name;
        @JsonProperty("remote")
        private boolean remote;
        @JsonProperty("hybrid")
        private boolean hybrid;

        public String getEventId() { return eventId; }
        public String getCode() { return code; }
        public String getRegionCode() { return regionCode; }
        public String getName() { return name; }
        public boolean isRemote() { return remote; }
        public boolean isHybrid() { return hybrid;}

        public Evt toEvent(){
            return new Evt( getEventId(),
                    getCode(),
                    getRegionCode(),
                    getName(),
                    isRemote(),
                    isHybrid()
            );
        }

        public Rgn toRgn(){
            return new Rgn( getRegionCode(), getRegionCode() );
        }
    }





}
