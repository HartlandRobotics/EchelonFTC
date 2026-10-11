package org.hartlandrobotics.echelonFTC.charts;

public class TeamNumberViewModel {
    private  Integer teamNumber;
    private  boolean isSelected;

    public TeamNumberViewModel( TeamData teamData ){
        this.teamNumber = teamData.getTeamNumber();
        this.isSelected = true;
    }

    public Integer getTeamNumber( ){
        return teamNumber;
    }

    public boolean getIsSelected(){
        return isSelected;
    }
    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
}
