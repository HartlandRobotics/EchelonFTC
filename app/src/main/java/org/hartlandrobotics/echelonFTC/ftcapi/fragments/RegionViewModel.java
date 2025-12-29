package org.hartlandrobotics.echelonFTC.ftcapi.fragments;

import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

public class RegionViewModel {
    private String regionKey;
    private String description;
    private boolean isSelected;

    public RegionViewModel(Rgn district) {
        this.regionKey = district.getRegionKey();
        this.description = district.getDescription();
        this.isSelected = false;
    }

    public String getRegionKey() {
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
