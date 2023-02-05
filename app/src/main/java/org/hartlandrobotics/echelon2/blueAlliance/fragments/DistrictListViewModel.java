package org.hartlandrobotics.echelon2.blueAlliance.fragments;

import org.hartlandrobotics.echelon2.database.entities.Region;

public class DistrictListViewModel {
    private String districtKey;
    private String abbreviation;
    private String displayName;
    private int year;
    private boolean isSelected;

    public DistrictListViewModel(Region region) {
        this.districtKey = region.getDistrictKey();
        this.abbreviation = region.getAbbreviation();
        this.displayName = region.getDisplayName();
        this.year = region.getYear();
        this.isSelected = false;
    }

    public String getDistrictKey() {
        return districtKey;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getYear() {
        return year;
    }

    public boolean getIsSelected() {
        return isSelected;
    }
    public void setIsSelected( boolean isSelected ){
        this.isSelected = isSelected;
    }
}
