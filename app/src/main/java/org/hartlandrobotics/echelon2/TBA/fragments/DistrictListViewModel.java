package org.hartlandrobotics.echelon2.TBA.fragments;

import org.hartlandrobotics.echelon2.database.entities.District;

public class DistrictListViewModel {
    private String districtKey;
    private String abbreviation;
    private String displayName;
    private int year;
    private boolean isSelected;

    public DistrictListViewModel(District district) {
        this.districtKey = district.getDistrictKey();
        this.abbreviation = district.getAbbreviation();
        this.displayName = district.getDisplayName();
        this.year = district.getYear();
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
