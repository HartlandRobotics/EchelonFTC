package org.hartlandrobotics.echelon2;

class MatchScheduleViewModel {
    private int matchNumber;

    private String red1;
    private String red2;
    private String red3;
    private String blue1;
    private String blue2;
    private String blue3;

    private int red1Average;
    private int red2Average;
    private int red3Average;
    private int blue1Average;
    private int blue2Average;
    private int blue3Average;

    public String getMatchNumber() { return String.valueOf(matchNumber); }
    public void setMatchNumber( int matchNumber ){
        this.matchNumber = matchNumber;
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

    public String getRed3() {
        return red3;
    }

    public void setRed3(String red3) {
        this.red3 = red3;
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

    public String getBlue3() {
        return blue3;
    }

    public void setBlue3(String blue3) {
        this.blue3 = blue3;
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

    public int getRed3Average() {
        return red3Average;
    }

    public void setRed3Average(int red3Average) {
        this.red3Average = red3Average;
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

    public int getBlue3Average() {
        return blue3Average;
    }

    public void setBlue3Average(int blue3Average) {
        this.blue3Average = blue3Average;
    }

    public int getRedTotal() {
        return red1Average + red2Average + red3Average;
    }

    public int getBlueTotal() {
        return blue1Average + blue2Average + blue3Average;
    }
}
