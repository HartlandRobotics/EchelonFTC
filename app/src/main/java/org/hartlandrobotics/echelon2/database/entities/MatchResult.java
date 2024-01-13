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
    private boolean autoParkBackstage;

    @ColumnInfo(name = "auto_white_pxl_purple_pxl")
    private boolean autoWhitePxlPurplePxl;

    @ColumnInfo( name = "auto_white_pxl_yellow_pxl")
    private boolean autoWhitePxlYellowPxl ;

    @ColumnInfo( name = "auto_team_purple_pxl")
    private boolean autoTeamPurplePxl;

    @ColumnInfo( name = "auto_team_yellow_pxl")
    private boolean autoTeamYellowPxl;

    @ColumnInfo( name = "auto_pxl_backdrop")
    private int autoPxlBackdrop;

    @ColumnInfo( name= "auto_pxl_backstage")
    private int autoPxlBackstage;

    @ColumnInfo( name = "teleOp_Pxl_Backstage" )
    private int teleOpPxlBackstage;

    @ColumnInfo( name = "teleOp_Pxl_Backdrop" )
    private int teleOpPxlBackdrop;

    @ColumnInfo( name = "teleOp_Artist" )
    private int teleOpArtist;

    @ColumnInfo( name = "teleOp_Set" )
    private int teleOpSet;

    @ColumnInfo( name = "teleOp_Drop" )
    private int teleOpDrop;

    @ColumnInfo( name = "end_park_backstage" )
    private boolean endParkBackstage;

    @ColumnInfo( name = "end_suspended" )
    private boolean endSuspended;

    @ColumnInfo( name = "end_landing_zone" )
    private int endLandingZone;

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

            boolean autoParkBackstage,
            boolean autoWhitePxlPurplePxl,
            boolean autoWhitePxlYellowPxl,
            boolean autoTeamPurplePxl,
            boolean autoTeamYellowPxl,
            int autoPxlBackdrop,
            int autoPxlBackstage,

            int teleOpPxlBackstage,
            int teleOpPxlBackdrop,
            int teleOpArtist,
            int teleOpSet,
            int teleOpDrop,

            boolean endParkBackstage,
            boolean endSuspended,
            int endLandingZone,

            String additionalNotes,
            int defenseCount
    ) {

        this.matchResultKey = StringUtils.defaultIfBlank(matchResultKey, UUID.randomUUID().toString());
        this.eventKey = eventKey;
        this.matchKey = matchKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;
        this.autoParkBackstage = autoParkBackstage;
        this.autoWhitePxlPurplePxl = autoWhitePxlPurplePxl;
        this.autoWhitePxlYellowPxl = autoWhitePxlYellowPxl;
        this.autoTeamPurplePxl = autoTeamPurplePxl;
        this.autoTeamYellowPxl = autoTeamYellowPxl;
        this.autoPxlBackdrop = autoPxlBackdrop;
        this.autoPxlBackstage = autoPxlBackstage;


        this.teleOpPxlBackstage = teleOpPxlBackstage;
        this.teleOpPxlBackdrop = teleOpPxlBackdrop;
        this.teleOpArtist = teleOpArtist;
        this.teleOpSet = teleOpSet;
        this.teleOpDrop = teleOpDrop;

        this.endParkBackstage = endParkBackstage;
        this.endSuspended = endSuspended;
        this.endLandingZone = endLandingZone;

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

    public boolean getAutoParkBackstage(){ return autoParkBackstage; }
    public void setAutoParkBackstage(boolean autoParkBackstage){ this.autoParkBackstage = autoParkBackstage; }
    public boolean getAutoWhitePxlPurplePxl() { return autoWhitePxlPurplePxl; }
    public void setAutoWhitePxlPurplePxl(boolean autoWhitePxlPurplePxl ){ this.autoWhitePxlPurplePxl = autoWhitePxlPurplePxl; }
    public boolean getAutoWhitePxlYellowPxl(){ return autoWhitePxlYellowPxl; }
    public void setAutoWhitePxlYellowPxl (boolean autoWhitePxlYellowPxl){ this.autoWhitePxlYellowPxl = autoWhitePxlYellowPxl; }
    public boolean getAutoTeamPurplePxl(){ return autoTeamPurplePxl;}
    public void setAutoTeamPurplePxl(boolean autoTeamPurplePxl){ this.autoTeamPurplePxl = autoTeamPurplePxl; }
    public boolean getAutoTeamYellowPxl(){ return autoTeamPurplePxl;}
    public void setAutoTeamYellowPxl(boolean autoTeamYellowPxl){ this.autoTeamYellowPxl = autoTeamYellowPxl; }
    public int getAutoPxlBackdrop(){ return autoPxlBackdrop; }
    public void setAutoPxlBackdrop(int autoPxlBackdrop) {
        this.autoPxlBackdrop = autoPxlBackdrop;
    }
    public int getAutoPxlBackstage(){ return autoPxlBackstage; }
    public void setAutoPxlBackstage( int autoPxlBackstage ){ this.autoPxlBackstage = autoPxlBackstage; }


    public int getTeleOpPxlBackstage(){ return teleOpPxlBackstage; }
    public void setTeleOpPxlBackstage(int teleOpPxlBackstage){ this.teleOpPxlBackstage = teleOpPxlBackstage; }
    public int getTeleOpPxlBackdrop(){ return teleOpPxlBackdrop;}
    public void setTeleOpPxlBackdrop( int teleOpPxlBackdrop )   { this.teleOpPxlBackdrop = teleOpPxlBackdrop; }
    public int getTeleOpArtist(){ return teleOpArtist; }
    public void setTeleOpArtist( int teleOpArtist ){
        this.teleOpArtist = teleOpArtist;
    }
    public int getTeleOpSet(){ return teleOpSet; }
    public void setTeleOpSet( int teleOpSet ){
        this.teleOpSet = teleOpSet;
    }
    public int getTeleOpDrop(){ return teleOpDrop; }
    public void setTeleOpDrop( int teleOpDrop){ this.teleOpDrop = teleOpDrop; }

    public boolean getEndParkBackstage(){ return endParkBackstage; }
    public void setEndParkBackstage(boolean endParkBackstage ){  this.endParkBackstage = endParkBackstage; }
    public boolean getEndSuspended(){ return endSuspended; }
    public void setEndSuspended( boolean endSuspended){ this.endSuspended = endSuspended; }
    public int getEndLandingZone(){ return endLandingZone; }
    public void setEndLandingZone( int endLandingZone ){ this.endLandingZone = endLandingZone; }

    public String getAdditionalNotes(){ return additionalNotes; }
    public void setAdditionalNotes( String additionalNotes ){
        this.additionalNotes = additionalNotes;
    }

    public int getDefenseCount(){ return defenseCount; }
    public void setDefenseCount( int defenseCount ){
        this.defenseCount = defenseCount;
    }
}
