package org.hartlandrobotics.echelonFTC.ftcapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ApiEvents {
    @JsonProperty("events")
    private List<ApiEvent> events;
    @JsonProperty("eventCount")
    private int eventCount;

    public List<ApiEvent> getEvents() {
        return events;
    }

    public int getEventCount() {
        return eventCount;
    }
}