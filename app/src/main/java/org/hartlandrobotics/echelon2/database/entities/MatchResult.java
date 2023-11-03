package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import java.util.UUID;

@Entity(tableName="match_result")
public class MatchResult {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "match_result_key")
    private String matchResultKey;

    @NonNull
    @ColumnInfo(name = "event_key")
    private String eventKey;

    @NonNull
    @ColumnInfo(name = "match_key")
    private String matchKey;

    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "has_been_synced")
    private boolean hasBeenSynced;

    @ColumnInfo(name="auto_park_backstage")
    private boolean parkBackstage;

    @ColumnInfo(name = "auto_white_pxl_purple_pxl")
    private int autoWhitePxlPurplePxl;

    @ColumnInfo( name = "auto_low_balls")
    private int autoLowBalls;

    @ColumnInfo( name = "auto_human_player_shots")
    private int autoHumanPlayerShots;

    @ColumnInfo( name = "teleop_high_balls")
    private int teleOpHighBalls;

    @ColumnInfo( name = "teleop_low_balls")
    private int teleOpLowBalls;

    @ColumnInfo( name = "end_hang_low")
    private boolean endHangLow;

    @ColumnInfo(name = "end_hang_mid")
    private boolean endHangMid;

    @ColumnInfo(name = "end_hang_high")
    private boolean endHangHigh;

    @ColumnInfo(name = "end_hang_traverse")
    private boolean endHangTraverse;

    @ColumnInfo(name = "additional_notes")
    private String additionalNotes;

    @ColumnInfo(name = "defense_count")
    private int defenseCount;

    @Ignore
    public MatchResult(){}

    public MatchResult(
            @NonNull String matchResultKey,
            @NonNull String eventKey,
            @NonNull String matchKey,
            @NonNull String teamKey,
            boolean hasBeenSynced,
            boolean parkBackstage,
            int autoWhitePxlPurplePxl,
            int autoLowBalls,
            int autoHumanPlayerShots,
            int teleOpHighBalls,
            int teleOpLowBalls,
            boolean endHangLow,
            boolean endHangMid,
            boolean endHangHigh,
            boolean endHangTraverse,
            String additionalNotes,
            int defenseCount
    ) {

        this.matchResultKey = StringUtils.defaultIfBlank(matchResultKey, UUID.randomUUID().toString());
        this.eventKey = eventKey;
        this.matchKey = matchKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;
        this.parkBackstage = parkBackstage;
        this.autoWhitePxlPurplePxl = autoWhitePxlPurplePxl;
        this.autoLowBalls = autoLowBalls;
        this.autoHumanPlayerShots = autoHumanPlayerShots;
        this.teleOpHighBalls = teleOpHighBalls;
        this.teleOpLowBalls = teleOpLowBalls;
        this.endHangLow = endHangLow;
        this.endHangMid = endHangMid;
        this.endHangHigh = endHangHigh;
        this.endHangTraverse = endHangTraverse;
        this.additionalNotes = additionalNotes;
        this.defenseCount = defenseCount;
    }

    public String getMatchResultKey() { return matchResultKey; }
    public void setMatchResultKey(String matchResultKey){
        this.matchResultKey = matchResultKey;
    }

    public String getEventKey(){ return eventKey; }
    public void setEventKey(String eventKey){
        this.eventKey = eventKey;
    }

    public String getMatchKey(){ return matchKey; }
    public void setMatchKey(String matchKey){ this.matchKey = matchKey; }

    public String getTeamKey(){ return teamKey; }
    public void setTeamKey(String teamKey){
        this.teamKey = teamKey;
    }

    public boolean getHasBeenSynced(){ return hasBeenSynced; }
    public void setHasBeenSynced(boolean hasBeenSynced){
        this.hasBeenSynced = hasBeenSynced;
    }

    public boolean getParkBackstage(){ return parkBackstage; }
    public void setParkBackstage(boolean taxiTarmac){ this.parkBackstage = parkBackstage; }

    public int getAutoWhitePxlPurplePxl() { return autoWhitePxlPurplePxl; }
    public void setAutoHighBalls(int autoWhitePxlPurplePxl ){
        this.autoWhitePxlPurplePxl = autoWhitePxlPurplePxl;
    }

    public int getAutoLowBalls(){ return autoLowBalls; }
    public void setAutoLowBalls(int autoLowBalls){
        this.autoLowBalls = autoLowBalls;
    }

    public int getAutoHumanPlayerShots(){ return autoHumanPlayerShots;}
    public void setAutoHumanPlayerShots(int autoHumanPlayerShots){
        this.autoHumanPlayerShots = autoHumanPlayerShots;
    }

    public int getTeleOpHighBalls() { return teleOpHighBalls; }
    public void setTeleOpHighBalls( int teleOpHighBalls ){
        this.teleOpHighBalls = teleOpHighBalls;
    }

    public int getTeleOpLowBalls(){ return teleOpLowBalls; }
    public void setTeleOpLowBalls( int teleOpLowBalls ){
        this.teleOpLowBalls = teleOpLowBalls;
    }

    public boolean getEndHangLow(){ return endHangLow; }
    public void setEndHangLow(boolean endHangLow){
        this.endHangLow = endHangLow;
    }

    public boolean getEndHangMid(){ return endHangMid; }
    public void setEndHangMid(boolean endHangMid){
        this.endHangMid = endHangMid;
    }

    public boolean getEndHangHigh(){ return endHangHigh; }
    public void setEndHangHigh(boolean endHangHigh){
        this.endHangHigh = endHangHigh;
    }

    public boolean getEndHangTraverse(){ return endHangTraverse; }
    public void setEndHangTraverse(boolean endHangTraverse){
        this.endHangTraverse = endHangTraverse;
    }

    public String getAdditionalNotes(){ return additionalNotes; }
    public void setAdditionalNotes( String additionalNotes ){
        this.additionalNotes = additionalNotes;
    }

    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.defenseCount = defenseCount;
    }
}
