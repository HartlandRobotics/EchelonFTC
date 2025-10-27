package org.hartlandrobotics.echelonFTC.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

public class SyncRegions {
    @JsonProperty("regionCode")
    private String regionCode;


    public String getRegionCode() {
        return regionCode;
    }

    public Rgn toRegion(){
        Rgn region = new Rgn(getRegionCode());
        return region;
    }

}

