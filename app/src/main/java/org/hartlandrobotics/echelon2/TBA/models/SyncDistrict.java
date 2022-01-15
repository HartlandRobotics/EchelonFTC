package org.hartlandrobotics.echelon2.TBA.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.District;

public class SyncDistrict {
    @JsonProperty("abbreviation")
    private String abbreviation;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("key")
    private String key;

    @JsonProperty("year")
    private int year;

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getKey() {
        return key;
    }

    public int getYear() {
        return year;
    }

    public District toDistrict(){
        District district = new District(getKey(), getAbbreviation(), getDisplayName(), getYear());
        return district;
    }

}

