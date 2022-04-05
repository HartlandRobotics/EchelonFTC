package org.hartlandrobotics.echelon2.charts;

public class TeamListViewModel {
    private String teamNumber;
    boolean isSelected;

    public TeamListViewModel( String teamNumber ){
        this.teamNumber = teamNumber;
        this.isSelected = true;
    }

    public String getTeamNumber( ){
        return teamNumber;
    }

    public int getTeamInteger(){
        return Integer.valueOf(teamNumber);
    }

    public boolean getIsSelected(){
        return isSelected;
    }
    public void setIsSelected(boolean isSelected){
        this.isSelected = isSelected;
    }

}
