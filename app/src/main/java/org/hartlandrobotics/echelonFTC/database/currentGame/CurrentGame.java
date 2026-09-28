package org.hartlandrobotics.echelonFTC.database.currentGame;

import org.hartlandrobotics.echelonFTC.database.entities.MatchResult;

public class CurrentGame {
    public MatchResult result = null;

    public CurrentGame(MatchResult result) {
        this.result = result;
    }

    public int getContribution(){
        return result.getContribution();
    }
    public int getTotalPoints(){
        return getAutoPoints() + getTeleOpPoints() + getEndPoints();
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
        autoPoints += this.getAuto8Points();
        autoPoints += this.getAuto9Points();
        autoPoints += this.getAuto10Points();
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
        autoCounts += this.getAuto8Counts();
        autoCounts += this.getAuto9Counts();
        autoCounts += this.getAuto10Counts();
        return autoCounts;
    }

    public int getAuto1Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag1() ? 1:0;
    }
    public int getAuto1Points(){
        if( result == null ) return 0;
        return result.getAutoFlag1()? 3:0;
    }

    public int getAuto2Counts(){
        if( result == null ) return 0;
        return result.getAutoFlag2() ? 1:0;
    }
    public int getAuto2Points(){
        if( result == null ) return 0;
        return result.getAutoFlag2() ? 5:0;
    }

    public int getAuto3Counts(){
//        if( result == null ) return 0;
        return 0;    }
    public int getAuto3Points(){
//        if( result == null ) return 0;
        return 0;
    }

    public int getAuto4Counts(){
//        if( result == null ) return 0;
        return 0;
    }
    public int getAuto4Points(){
//        if( result == null ) return 0;
        return 0;
    }

    public int getAuto5Counts(){
//        if( result == null ) return 0;
        return 0;    }
    public int getAuto5Points(){
//        if( result == null ) return 0;
        return 0;
    }

    public int getAuto6Counts(){
        if( result == null ) return 0;
        return result.getAutoInt6();
    }
    public int getAuto6Points(){
        if( result == null ) return 0;
        return result.getAutoInt6() * 20;
    }

    public int getAuto7Counts(){
        if( result == null ) return 0;
        return result.getAutoInt7();
    }
    public int getAuto7Points(){
        if( result == null ) return 0;
        return result.getAutoInt7() * 1;
    }
    public int getAuto8Counts(){
        if( result == null ) return 0;
        return result.getAutoInt8();
    }
    public int getAuto8Points(){
        if( result == null ) return 0;
        return result.getAutoInt8() * 2;
    }

    public int getAuto9Counts(){
//        if( result == null ) return 0;
        return 0;
    }
    public int getAuto9Points(){
        if( result == null ) return 0;
        return result.getAutoInt9() * 0;
    }

    public int getAuto10Counts(){
//        if( result == null ) return 0;
        return 0;
    }
    public int getAuto10Points(){
        if( result == null ) return 0;
        return result.getAutoInt10() * 0;
    }


    public int getTeleOpPoints() {
        int teleOpPoints = 0;
        teleOpPoints += this.getTeleOp6Points();
        teleOpPoints += this.getTeleOp7Points();
        teleOpPoints += this.getTeleOp8Points();
        teleOpPoints += this.getTeleOp9Points();
        teleOpPoints += this.getTeleOp10Points();
        teleOpPoints += this.getTeleOp11Points();
        return teleOpPoints;
    }
    public int getTeleOpCounts() {
        int teleOpCounts = 0;
        teleOpCounts += this.getTeleOp6Counts();
        teleOpCounts += this.getTeleOp7Counts();
        teleOpCounts += this.getTeleOp8Counts();
        teleOpCounts += this.getTeleOp9Counts();
        teleOpCounts += this.getTeleOp10Counts();
        teleOpCounts += this.getTeleOp11Counts();
        return teleOpCounts;
    }

    public int getTeleOp6Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt6();
    }
    public int getTeleOp6Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt6() * 3;
    }

    public int getTeleOp7Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt7();
    }
    public int getTeleOp7Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt7() * 1;
    }

    public int getTeleOp8Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt8();
    }
    public int getTeleOp8Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt8() * 1;
    }


    public int getTeleOp9Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt9();
    }
    public int getTeleOp9Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt9() * 2;
    }

    public int getTeleOp10Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt10();
    }
    public int getTeleOp10Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt10() * 0;
    }

    public int getTeleOp11Counts(){
        if( result == null ) return 0;
        return result.getTeleOpInt11();
    }
    public int getTeleOp11Points(){
        if( result == null ) return 0;
        return result.getTeleOpInt11() * 0;
    }

    public int getEndPoints() {
        int endPoints = 0;
        endPoints += this.getEnd1Points();
        endPoints += this.getEnd2Points();
        endPoints += this.getEnd3Points();
        endPoints += this.getEnd4Points();

        endPoints += this.getEnd6Points();
        return endPoints;
    }
    public int getEndCounts() {
        int endCounts = 0;
        endCounts += this.getEnd1Counts();
        endCounts += this.getEnd2Counts();
        endCounts += this.getEnd3Counts();
        endCounts += this.getEnd4Counts();
        endCounts += this.getEnd6Counts();
        return endCounts;
    }

    public int getEnd1Counts(){
        if( result == null ) return 0;
        return result.getEndFlag1() ?  1:0;
    }
    public int getEnd1Points(){
        if( result == null ) return 0;
        return result.getEndFlag1() ? 5:0;
    }

    public int getEnd2Counts(){
        //if( result == null ) return 0;
        return 0;
    }
    public int getEnd2Points(){
//        if( result == null ) return 0;
        return 0;
    }
    public int getEnd3Counts(){
        if( result == null ) return 0;
        return result.getEndFlag3() ? 1:0;
    }
    public int getEnd3Points(){
//        if( result == null ) return 0;
        return 0;
    }
    public int getEnd4Counts(){
        if( result == null ) return 0;
        return result.getEndFlag4() ? 1:0;
    }
    public int getEnd4Points(){
//        if( result == null ) return 0;
        return 0;
    }

    public int getEnd6Counts(){
        if( result == null ) return 0;
        return result.getEndInt6();
    }
    public int getEnd6Points(){
        if( result == null ) return 0;
        return result.getEndInt6() * 2;
    }

}
