package org.hartlandrobotics.echelon2.blueAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.District;

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

    public District toDistrict(){
        District district = new District(getRegionKey(), getDescription());
        return district;
    }

}

