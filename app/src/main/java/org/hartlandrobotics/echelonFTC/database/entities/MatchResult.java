package org.hartlandrobotics.echelonFTC.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.database.currentGame.CurrentGame;

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

    @ColumnInfo(name="auto_flag_1")
    private boolean autoFlag1;

    @ColumnInfo(name = "auto_flag_2")
    private boolean autoFlag2;

    @ColumnInfo( name = "auto_flag_3")
    private boolean autoFlag3 ;

    @ColumnInfo( name = "auto_flag_4")
    private boolean autoFlag4;

    @ColumnInfo( name = "auto_flag_5")
    private boolean autoFlag5;

    @ColumnInfo( name = "auto_int_6")
    private int autoInt6;

    @ColumnInfo( name= "auto_int_7")
    private int autoInt7;

    @ColumnInfo( name= "auto_int_8")
    private int autoInt8;

    @ColumnInfo( name= "auto_int_9")
    private int autoInt9;

    @ColumnInfo( name= "auto_int_10")
    private int autoInt10;

    @ColumnInfo( name = "teleOp_int_1" )
    private int teleOpInt1;

    @ColumnInfo( name = "teleOp_int_2" )
    private int teleOpInt2;

    @ColumnInfo( name = "teleOp_int_3" )
    private int teleOpInt3;

    @ColumnInfo( name = "teleOp_int_4" )
    private int teleOpInt4;

    @ColumnInfo( name = "teleOp_int_5" )
    private int teleOpInt5;

    @ColumnInfo( name = "end_flag_1" )
    private boolean endFlag1;

    @ColumnInfo( name = "end_flag_2" )
    private boolean endFlag2;

    @ColumnInfo( name = "end_flag_3" )
    private boolean endFlag3;
    @ColumnInfo( name = "end_flag_4" )
    private boolean endFlag4;
    @ColumnInfo( name = "end_int_6" )
    private int endInt6;

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

            boolean autoFlag1,
            boolean autoFlag2,
            boolean autoFlag3,
            boolean autoFlag4,
            boolean autoFlag5,
            int autoInt6,
            int autoInt7,
            int autoInt8,
            int autoInt9,
            int autoInt10,

            int teleOpInt1,
            int teleOpInt2,
            int teleOpInt3,
            int teleOpInt4,
            int teleOpInt5,

            boolean endFlag1,
            boolean endFlag2,
            boolean endFlag3,
            boolean endFlag4,
            int endInt6,

            String additionalNotes,
            int defenseCount
    ) {

        this.matchResultKey = StringUtils.defaultIfBlank(matchResultKey, UUID.randomUUID().toString());
        this.eventKey = eventKey;
        this.matchKey = matchKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;

        this.autoFlag1 = autoFlag1;
        this.autoFlag2 = autoFlag2;
        this.autoFlag3 = autoFlag3;
        this.autoFlag4 = autoFlag4;
        this.autoFlag5 = autoFlag5;
        this.autoInt6 = autoInt6;
        this.autoInt7 = autoInt7;
        this.autoInt8 = autoInt8;
        this.autoInt9 = autoInt9;
        this.autoInt10 = autoInt10;


        this.teleOpInt1 = teleOpInt1;
        this.teleOpInt2 = teleOpInt2;
        this.teleOpInt3 = teleOpInt3;
        this.teleOpInt4 = teleOpInt4;
        this.teleOpInt5 = teleOpInt5;

        this.endFlag1 = endFlag1;
        this.endFlag2 = endFlag2;
        this.endFlag3 = endFlag3;
        this.endFlag4 = endFlag4;

        this.endInt6 = endInt6;

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

    public boolean getAutoFlag1(){ return autoFlag1; }
    public void setAutoFlag1(boolean autoFlag1){ this.autoFlag1 = autoFlag1; }
    public boolean getAutoFlag2() { return autoFlag2; }
    public void setAutoFlag2(boolean autoFlag2 ){ this.autoFlag2 = autoFlag2; }
    public boolean getAutoFlag3(){ return autoFlag3; }
    public void setAutoFlag3 (boolean autoFlag3){ this.autoFlag3 = autoFlag3; }
    public boolean getAutoFlag4(){ return autoFlag4;}
    public void setAutoFlag4(boolean autoTeamFlag4){ this.autoFlag4 = autoFlag4; }
    public boolean getAutoFlag5(){ return autoFlag5;}
    public void setAutoFlag5(boolean autoFlag5){ this.autoFlag5 = autoFlag5; }
    public int getAutoInt6(){ return autoInt6; }
    public void setAutoInt6(int autoInt6) {
        this.autoInt6 = autoInt6;
    }
    public int getAutoInt7(){ return autoInt7; }
    public void setAutoInt7( int autoInt7 ){ this.autoInt7 = autoInt7; }
    public int getAutoInt8(){ return autoInt8; }
    public void setAutoInt8( int autoInt8 ){ this.autoInt8 = autoInt8; }
    public int getAutoInt9(){ return autoInt9; }
    public void setAutoInt9( int autoInt9 ){ this.autoInt9 = autoInt9; }
    public int getAutoInt10(){ return autoInt10; }
    public void setAutoInt10( int autoInt10 ){ this.autoInt10 = autoInt10; }


    public int getTeleOpInt1(){ return teleOpInt1; }
    public void setTeleOpInt1(int teleOpInt1){ this.teleOpInt1 = teleOpInt1; }
    public int getTeleOpInt2(){ return teleOpInt2;}
    public void setTeleOpInt2( int teleOpInt2 )   { this.teleOpInt2 = teleOpInt2; }
    public int getTeleOpInt3(){ return teleOpInt3; }
    public void setTeleOpInt3( int teleOpInt3 ){
        this.teleOpInt3 = teleOpInt3;
    }
    public int getTeleOpInt4(){ return teleOpInt4; }
    public void setTeleOpInt4( int teleOpInt4 ){
        this.teleOpInt4 = teleOpInt4;
    }
    public int getTeleOpInt5(){ return teleOpInt5; }
    public void setTeleOpInt5( int teleOpInt5 ){ this.teleOpInt5 = teleOpInt5; }

    public boolean getEndFlag1(){ return endFlag1; }
    public void setEndFlag1(boolean endFlag1 ){  this.endFlag1 = endFlag1; }
    public boolean getEndFlag2(){ return endFlag2; }
    public void setEndFlag2( boolean endFlag2){ this.endFlag2 = endFlag2; }
    public boolean getEndFlag3(){ return endFlag3; }
    public void setEndFlag3(boolean endFlag3 ){  this.endFlag3 = endFlag3; }
    public boolean getEndFlag4(){ return endFlag4; }
    public void setEndFlag4( boolean endFlag4){ this.endFlag4 = endFlag4; }


    public int getEndInt6(){ return endInt6; }
    public void setEndInt6( int endInt6 ){ this.endInt6 = endInt6; }

    public String getAdditionalNotes(){ return additionalNotes; }
    public void setAdditionalNotes( String additionalNotes ){
        this.additionalNotes = additionalNotes;
    }

    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.defenseCount = defenseCount;
    }

    public static CurrentGame toCurrentGamePoints(MatchResult matchResult){
        return new CurrentGame(matchResult);
    }
}
