package org.hartlandrobotics.echelon2.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.Region;

public class SyncRegion {
    @JsonProperty("region_key")
    private String regionKey;

    @JsonProperty("description")
    private String description;

    public String getRegionKey() {
        return regionKey;
    }

    public String getDescription() {
        return description;
    }

    public Region toRegion(){
        Region region = new Region(getRegionKey(), getDescription());
        return region;
    }

}

