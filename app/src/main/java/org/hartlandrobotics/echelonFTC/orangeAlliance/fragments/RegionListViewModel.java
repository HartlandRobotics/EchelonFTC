package org.hartlandrobotics.echelonFTC.orangeAlliance.fragments;

import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

public class RegionListViewModel {
    private final String regionCode;
    private boolean isSelected;

    public RegionListViewModel(Rgn district) {
        this.regionCode = district.getRegionCode();
        this.isSelected = false;
    }

    public String getRegionCode() {
        return regionCode;
    }
    public boolean getIsSelected() {
        return isSelected;
    }
    public void setIsSelected( boolean isSelected ){
        this.isSelected = isSelected;
    }
}
