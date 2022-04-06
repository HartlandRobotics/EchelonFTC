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
    private int red1Cargo;
    private int red1Hang;
    private int red2Average;
    private int red2Cargo;
    private int red2Hang;
    private int red3Average;
    private int red3Cargo;
    private int red3Hang;
    private int blue1Average;
    private int blue1Cargo;
    private int blue1Hang;
    private int blue2Average;
    private int blue2Cargo;
    private int blue2Hang;
    private int blue3Average;
    private int blue3Cargo;
    private int blue3Hang;

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

    public int getRed1Cargo() { return red1Cargo; }
    public void setRed1Cargo(int red1Cargo){ this.red1Cargo = red1Cargo; }
    public int getRed1Hang() { return red1Hang; }
    public void setRed1Hang(int red1Hang){ this.red1Hang = red1Hang; }

    public int getRed2Cargo() { return red2Cargo; }
    public void setRed2Cargo(int red2Cargo){ this.red2Cargo = red2Cargo; }
    public int getRed2Hang() { return red2Hang; }
    public void setRed2Hang(int red2Hang){ this.red2Hang = red2Hang; }

    public int getRed3Cargo() { return red3Cargo; }
    public void setRed3Cargo(int red3Cargo){ this.red3Cargo = red3Cargo; }
    public int getRed3Hang() { return red3Hang; }
    public void setRed3Hang(int red3Hang){ this.red3Hang = red3Hang; }

    public int getBlue1Cargo() { return blue1Cargo; }
    public void setBlue1Cargo(int blue1Cargo){ this.blue1Cargo = blue1Cargo; }
    public int getBlue1Hang() { return blue1Hang; }
    public void setBlue1Hang(int blue1Hang){ this.blue1Hang = blue1Hang; }

    public int getBlue2Cargo() { return blue2Cargo; }
    public void setBlue2Cargo(int blue2Cargo){ this.blue2Cargo = blue2Cargo; }
    public int getBlue2Hang() { return blue2Hang; }
    public void setBlue2Hang(int blue2Hang){ this.blue2Hang = blue2Hang; }

    public int getBlue3Cargo() { return blue3Cargo; }
    public void setBlue3Cargo(int blue3Cargo){ this.blue3Cargo = blue3Cargo; }
    public int getBlue3Hang() { return blue3Hang; }
    public void setBlue3Hang(int blue3Hang){ this.blue3Hang = blue3Hang; }

    public int getRedCargoTotal(){ return getRed1Cargo() + getRed2Cargo() + getRed3Cargo(); }
    public int getBlueCargoTotal(){ return getBlue1Cargo() + getBlue2Cargo() + getBlue3Cargo(); }

    public int getRedHangTotal() { return getRed1Hang() + getRed2Hang() + getRed3Hang(); }
    public int getBlueHangTotal() { return getBlue1Hang() + getBlue2Hang() + getBlue3Hang(); }


}
