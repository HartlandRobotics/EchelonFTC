package org.hartlandrobotics.echelonFTC.charts;


public class AggAverageViewModel {
    private final int teamNumber;
    private final boolean isVisible;
    private final float autoAverage;
    private final float teleOpAverage;
    private final float endGameAverage;
    private final float oprAverage;
    private final float totalAverage;

    public AggAverageViewModel(TeamData teamData) {
        int size = teamData.getSize();

        this.teamNumber = teamData.getTeamNumber();
        this.isVisible = teamData.getVisible();
        this.autoAverage = (float) teamData.getAutoTotalPoints() / size;
        this.teleOpAverage = (float) teamData.getTeleOpPoints() / size;
        this.endGameAverage = (float) teamData.getEndGamePoints() / size;
        this.oprAverage = (float) teamData.getOprPoints() / size;
        this.totalAverage = (float) teamData.getTotalPoints() / size;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public float getAutoAverage() {
        return autoAverage;
    }

    public float getTeleOpAverage() {
        return teleOpAverage;
    }

    public float getEndGameAverage() {
        return endGameAverage;
    }

    public float getOprAverage() {
        return oprAverage;
    }

    public float getTotalAverage() {
        return totalAverage;
    }

}
