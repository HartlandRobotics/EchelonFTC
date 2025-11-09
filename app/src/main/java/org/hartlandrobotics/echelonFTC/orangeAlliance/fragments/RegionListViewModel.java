package org.hartlandrobotics.echelonFTC.orangeAlliance.fragments;

import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

public class RegionListViewModel {
    private final String regionCode;

    public final String name;
    private boolean isSelected;

    public RegionListViewModel(Rgn region) {
        this.regionCode = region.getRegionCode();
        this.name = region.getName();
        this.isSelected = false;
    }

    public String getRegionCode() {
        return regionCode;
    }
    public String getName(){ return name; }
    public boolean getIsSelected() {
        return isSelected;
    }
    public void setIsSelected( boolean isSelected ){
        this.isSelected = isSelected;
    }
}
