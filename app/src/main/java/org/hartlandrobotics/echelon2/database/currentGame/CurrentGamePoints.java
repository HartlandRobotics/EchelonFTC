package org.hartlandrobotics.echelon2.database.currentGame;

import org.hartlandrobotics.echelon2.database.entities.MatchResult;

public class CurrentGamePoints {
    public MatchResult result = null;

    public CurrentGamePoints(MatchResult result) {
        this.result = result;
    }

    public int getAutoPoints() {
        int autoPoints = 0;
        autoPoints += this.getAuto1Points();
        autoPoints += this.getAuto2Points();
        autoPoints += this.getAuto3Points();
        autoPoints += this.getAuto4Points();
        autoPoints += this.getAuto5Points();
        autoPoints += this.getAuto6Points();
        autoPoints += this.getAuto7Points();
        return autoPoints;
    }
    public int getAutoCounts() {
        int autoCounts = 0;
        autoCounts += this.getAuto1Counts();
        autoCounts += this.getAuto2Counts();
        autoCounts += this.getAuto3Counts();
        autoCounts += this.getAuto4Counts();
        autoCounts += this.getAuto5Counts();
        autoCounts += this.getAuto6Counts();
        autoCounts += this.getAuto7Counts();
        return autoCounts;
    }

    public int getAuto1Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag1() ? 1:0;
    }
    public int getAuto1Points(){
        if( result == null ) return 0;
        return result.getAutoFlag1()? 1:0;
    }

    public int getAuto2Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag2() ? 1:0;
    }
    public int getAuto2Points(){
        if( result == null ) return 0;
        return result.getAutoFlag2()? 1:0;
    }

    public int getAuto3Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag3() ? 1:0;
    }
    public int getAuto3Points(){
        if( result == null ) return 0;
        return result.getAutoFlag3()? 1:0;
    }

    public int getAuto4Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag4() ? 1:0;
    }
    public int getAuto4Points(){
        if( result == null ) return 0;
        return result.getAutoFlag4()? 1:0;
    }

    public int getAuto5Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag5() ? 1:0;
    }
    public int getAuto5Points(){
        if( result == null ) return 0;
        return result.getAutoFlag5()? 1:0;
    }

    public int getAuto6Counts(){
        if( result == null ) return 0;
        return result.getAutoInt6();
    }
    public int getAuto6Points(){
        if( result == null ) return 0;
        return result.getAutoInt6() * 1;
    }

    public int getAuto7Counts(){
        if( result == null ) return 0;
        return result.getAutoInt7();
    }
    public int getAuto7Points(){
        if( result == null ) return 0;
        return result.getAutoInt7() * 1;
    }



    public int getTeleOpPoints() {
        int teleOpPoints = 0;
        teleOpPoints += this.getTeleOp1Points();
        teleOpPoints += this.getTeleOp2Points();
        teleOpPoints += this.getTeleOp3Points();
        teleOpPoints += this.getTeleOp4Points();
        teleOpPoints += this.getTeleOp5Points();
        return teleOpPoints;
    }
    public int getTeleOpCounts() {
        int teleOpCounts = 0;
        teleOpCounts += this.getTeleOp1Counts();
        teleOpCounts += this.getTeleOp2Counts();
        teleOpCounts += this.getTeleOp3Counts();
        teleOpCounts += this.getTeleOp4Counts();
        teleOpCounts += this.getTeleOp5Counts();
        return teleOpCounts;
    }

    public int getTeleOp1Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt1();
    }
    public int getTeleOp1Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt1() * 1;
    }

    public int getTeleOp2Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt2();
    }
    public int getTeleOp2Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt2() * 1;
    }

    public int getTeleOp3Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt3();
    }
    public int getTeleOp3Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt3() * 1;
    }


    public int getTeleOp4Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt4();
    }
    public int getTeleOp4Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt4() * 1;
    }

    public int getTeleOp5Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt5();
    }
    public int getTeleOp5Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt5() * 1;
    }

    public int getEndPoints() {
        int endPoints = 0;
        endPoints += this.getEnd1Points();
        endPoints += this.getEnd2Points();
        endPoints += this.getEnd6Points();
        return endPoints;
    }
    public int getEndCounts() {
        int endCounts = 0;
        endCounts += this.getEnd1Counts();
        endCounts += this.getEnd2Counts();
        endCounts += this.getEnd6Counts();
        return endCounts;
    }

    public int getEnd1Counts(){
        if( result == null ) return 0;
        return result.getEndFlag1() ?  1:0;
    }
    public int getEnd1Points(){
        if( result == null ) return 0;
        return result.getEndFlag1() ? 1:0;
    }

    public int getEnd2Counts(){
        if( result == null ) return 0;
        return result.getEndFlag2() ? 1:0;
    }
    public int getEnd2Points(){
        if( result == null ) return 0;
        return result.getEndFlag2() ? 1:0;
    }

    public int getEnd6Counts(){
        if( result == null ) return 0;
        return result.getEndInt6();
    }
    public int getEnd6Points(){
        if( result == null ) return 0;
        return result.getEndInt6() * 1;
    }

}
