package org.hartlandrobotics.echelonFTC.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.Evt;

public class SyncEvent {

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

    public Evt toEvent(){
        Evt event = new Evt(getEventId(), getCode(), getRegionCode(), getName(), isRemote(), isHybrid() );
        return event;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getRegionCode() { return regionCode; }
    public void setRegionCode(String regionCode) { this.regionCode = regionCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public boolean isRemote() { return remote; }
    public void setRemote(boolean remote) { this.remote = remote; }

    public boolean isHybrid() { return hybrid;}

    public void setHybrid(boolean hybrid) { this.hybrid = hybrid; }

}
