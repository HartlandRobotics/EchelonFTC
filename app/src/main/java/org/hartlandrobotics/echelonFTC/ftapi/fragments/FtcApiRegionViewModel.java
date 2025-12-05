package org.hartlandrobotics.echelonFTC.ftapi.fragments;

import org.hartlandrobotics.echelonFTC.database.entities.Rgn;

public class FtcApiRegionViewModel {
    private String regionKey;
    private String description;
    private boolean isSelected;

    public FtcApiRegionViewModel(Rgn district) {
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
