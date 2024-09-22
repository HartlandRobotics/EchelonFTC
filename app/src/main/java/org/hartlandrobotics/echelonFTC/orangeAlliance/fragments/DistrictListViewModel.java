package org.hartlandrobotics.echelonFTC.orangeAlliance.fragments;

import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

public class DistrictListViewModel {
    private String regionKey;
    private String description;
    private boolean isSelected;

    public DistrictListViewModel(Rgn district) {
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
