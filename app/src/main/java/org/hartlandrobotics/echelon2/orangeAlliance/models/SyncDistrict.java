package org.hartlandrobotics.echelon2.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.Rgn;

public class SyncDistrict {
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

    public Rgn toDistrict(){
        Rgn district = new Rgn(getRegionKey(), getDescription());
        return district;
    }

}

