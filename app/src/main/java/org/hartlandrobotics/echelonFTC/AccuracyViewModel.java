package org.hartlandrobotics.echelonFTC;

import org.apache.commons.lang3.StringUtils;

public class AccuracyViewModel {
    private int matchNumber = 0;
    private String allianceColor = StringUtils.EMPTY;
    private int blueAlliancePoints = 0;
    private int studentPoints = 0;
    private double percentInaccuracy = 0.0;
    private String tablet1Name = StringUtils.EMPTY;
    private String tablet2Name = StringUtils.EMPTY;

    public AccuracyViewModel() {
    }

    public int getMatchNumber() { return matchNumber; }
    public void setMatchNumber(int matchNumber) { this.matchNumber = matchNumber; }

    public String getAllianceColor() { return allianceColor; }
    public void setAllianceColor(String allianceColor) { this.allianceColor = allianceColor; }

    public int getBlueAlliancePoints() { return blueAlliancePoints; }
    public void setBlueAlliancePoints(int blueAlliancePoints) { this.blueAlliancePoints = blueAlliancePoints; }

    public int getStudentPoints() { return studentPoints; }
    public void setStudentPoints(int studentPoints) { this.studentPoints = studentPoints; }

    public double getPercentInaccuracy() { return percentInaccuracy; }
    public void setPercentInaccuracy(double percentInaccuracy) { this.percentInaccuracy = percentInaccuracy; }

    public String getTablet1Name() { return tablet1Name; }
    public void setTablet1Name(String tablet1Name) { this.tablet1Name = tablet1Name; }

    public String getTablet2Name() { return tablet2Name; }
    public void setTablet2Name(String tablet2Name) { this.tablet2Name = tablet2Name; }
}
