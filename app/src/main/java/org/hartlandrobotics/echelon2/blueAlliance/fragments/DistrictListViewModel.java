package org.hartlandrobotics.echelon2.blueAlliance.fragments;

import org.hartlandrobotics.echelon2.database.entities.District;

public class DistrictListViewModel {
    private String regionKey;
    private String description;
    private boolean isSelected;

    public DistrictListViewModel(District district) {
        this.regionKey = district.getRegionKey();
        this.description = district.getDescription();
        this.isSelected = false;
    }

    public String getDistrictKey() {
        return regionKey;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsSelected() {
        return isSelected;
    }
    public void setIsSelected( boolean isSelected ){
        this.isSelected = isSelected;
    }
}
