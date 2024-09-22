package org.hartlandrobotics.echelonFTC;

import org.apache.commons.math3.distribution.TDistribution;

class MatchScheduleViewModel {
    private String matchName;

    private String red1;
    private String red2;
    private String blue1;
    private String blue2;

    // todo: update to be generic scores

    private int red1Average;
    private int red1Auto;
    private int red1TeleOp;
    private int red1End;
    private double red1StdDeviation;

    private int red2Average;
    private int red2Auto;
    private int red2TeleOp;
    private int red2End;
    private double red2StdDeviation;

    private int blue1Average;
    private int blue1Auto;
    private int blue1TeleOp;
    private int blue1End;
    private double blue1StdDeviation;

    private int blue2Average;
    private int blue2Auto;
    private int blue2TeleOp;
    private int blue2End;
    private double blue2StdDeviation;

    private int matchCount;

    MatchScheduleViewModel() {
    }


    public String getMatchName() { return String.valueOf(matchName); }
    public void setMatchName( String matchName ){
        this.matchName = matchName;
    }

    public String getRed1() {
        return red1;
    }

    public void setRed1(String red1) {
        this.red1 = red1;
    }

    public String getRed2() {
        return red2;
    }

    public void setRed2(String red2) {
        this.red2 = red2;
    }

    public String getBlue1() {
        return blue1;
    }

    public void setBlue1(String blue1) {
        this.blue1 = blue1;
    }

    public String getBlue2() {
        return blue2;
    }

    public void setBlue2(String blue2) {
        this.blue2 = blue2;
    }

    public int getRed1Average() {
        return red1Average;
    }

    public void setRed1Average(int red1Average) {
        this.red1Average = red1Average;
    }

    public int getRed2Average() {
        return red2Average;
    }

    public void setRed2Average(int red2Average) {
        this.red2Average = red2Average;
    }

    public int getBlue1Average() {
        return blue1Average;
    }

    public void setBlue1Average(int blue1Average) {
        this.blue1Average = blue1Average;
    }

    public int getBlue2Average() {
        return blue2Average;
    }

    public void setBlue2Average(int blue2Average) {
        this.blue2Average = blue2Average;
    }


    public int getRedTotal() {
        return red1Average + red2Average;
    }

    public int getBlueTotal() {
        return blue1Average + blue2Average;
    }

    public int getRed1Auto() { return red1Auto; }
    public void setRed1Auto(int red1Auto){ this.red1Auto = red1Auto; }
    public int getRed1TeleOp() { return red1TeleOp; }
    public void setRed1TeleOp(int red1TeleOp){ this.red1TeleOp = red1TeleOp; }
    public int getRed1End() { return red1End; }
    public void setRed1End(int red1End){ this.red1End = red1End; }


    public int getRed2Auto() { return red2Auto; }
    public void setRed2Auto(int red2Auto){ this.red2Auto = red2Auto; }
    public int getRed2TeleOp() { return red2TeleOp; }
    public void setRed2TeleOp(int red2TeleOp){ this.red2TeleOp = red2TeleOp; }
    public int getRed2End() { return red2End; }
    public void setRed2End(int red2End){ this.red2End = red2End; }


    public int getBlue1Auto() { return blue1Auto; }
    public void setBlue1Auto(int blue1Auto){ this.blue1Auto = blue1Auto; }
    public int getBlue1TeleOp() { return blue1TeleOp; }
    public void setBlue1TeleOp(int blue1TeleOp){ this.blue1TeleOp = blue1TeleOp; }
    public int getBlue1End() { return blue1End; }
    public void setBlue1End(int blue1End){ this.blue1End = blue1End; }

    public int getBlue2Auto() { return blue2Auto; }
    public void setBlue2Auto(int blue2Auto){ this.blue2Auto = blue2Auto; }
    public int getBlue2TeleOp() { return blue2TeleOp; }
    public void setBlue2TeleOp(int blue2TeleOp){ this.blue2TeleOp = blue2TeleOp; }
    public int getBlue2End() { return blue2End; }
    public void setBlue2End(int blue2End){ this.blue2End = blue2End; }


    public int getRedAutoTotal(){ return getRed1Auto() + getRed2Auto(); }
    public int getBlueAutoTotal(){ return getBlue1Auto() + getBlue2Auto(); }

    public int getRedTeleOpTotal() { return getRed1TeleOp() + getRed2TeleOp(); }
    public int getBlueTeleOpTotal() { return getBlue1TeleOp() + getBlue2TeleOp(); }

    public int getRedEndTotal() { return getRed1End() + getRed2End(); }
    public int getBlueEndTotal() { return getBlue1End() + getBlue2End(); }

    public int getRedConfidenceIntervalMin() { return Math.max(0,getRedTotal() - getRedMarginOfError()); }
    public int getBlueConfidenceIntervalMin() { return Math.max(0, getBlueTotal() - getBlueMarginOfError()); }
    public int getRedConfidenceIntervalMax() { return getRedTotal() + getRedMarginOfError(); }
    public int getBlueConfidenceIntervalMax() { return getBlueTotal() + getBlueMarginOfError(); }

    public int getRedPercentage() {
        if(matchCount < 2){
            return 50;
        }
        double normalizedStat = (getRedTotal() - getBlueTotal()) / Math.sqrt(getRedTotalStdDeviation() * getRedTotalStdDeviation() + getBlueTotalStdDeviation() * getBlueTotalStdDeviation());
        TDistribution tDif = new TDistribution(matchCount - 1);
        //I'm pretty sure this is the right polarity, but avg might need to not be negative...
        //get the difference between the average scores, then put that in terms of standard deviations
        //using that, calculate the probability of that difference being exceeded based on the sample
        return (int) ((1-tDif.cumulativeProbability(-normalizedStat)) * 100);
    }
    public int getBluePercentage() { return 100 - getRedPercentage(); }

    public int getRedMarginOfError(){
        if(matchCount < 2){
            return 0;
        }
        //70% confidence interval
        return (int)(Math.abs(new TDistribution(matchCount - 1).
                inverseCumulativeProbability(.15))
                * getRedTotalStdDeviation());
    }
    public int getBlueMarginOfError(){
        if(matchCount < 2){
            return 0;
        }
        return (int)(Math.abs(new TDistribution(matchCount - 1).
                inverseCumulativeProbability(.15))
                * getBlueTotalStdDeviation());
    }

    public double getRedTotalStdDeviation(){
//        System.out.println("red std dev: " + Math.sqrt(red1StdDeviation * red1StdDeviation + red2StdDeviation * red2StdDeviation + red3StdDeviation * red3StdDeviation));
        return Math.sqrt(red1StdDeviation * red1StdDeviation + red2StdDeviation * red2StdDeviation);
    }

    public double getBlueTotalStdDeviation(){
        //System.out.println("blue std dev: " + Math.sqrt(blue1StdDeviation * blue1StdDeviation + blue2StdDeviation * blue2StdDeviation + blue3StdDeviation * blue3StdDeviation));
        return Math.sqrt(blue1StdDeviation * blue1StdDeviation + blue2StdDeviation * blue2StdDeviation);
    }
    public double getRed1StdDeviation() {
        return red1StdDeviation;
    }

    public void setRed1StdDeviation(double red1StdDeviation) {
        this.red1StdDeviation = red1StdDeviation;
    }

    public double getRed2StdDeviation() {
        return red2StdDeviation;
    }

    public void setRed2StdDeviation(double red2StdDeviation) {
        this.red2StdDeviation = red2StdDeviation;
    }


    public double getBlue1StdDeviation() {
        return blue1StdDeviation;
    }

    public void setBlue1StdDeviation(double blue1StdDeviation) {
        this.blue1StdDeviation = blue1StdDeviation;
    }

    public double getBlue2StdDeviation() {
        return blue2StdDeviation;
    }

    public void setBlue2StdDeviation(double blue2StdDeviation) {
        this.blue2StdDeviation = blue2StdDeviation;
    }

    public int getMatchCount() {
        return matchCount;
    }

    public void setMatchCount(int matchCount) {
        this.matchCount = matchCount;
    }
}
