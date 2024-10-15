package org.hartlandrobotics.echelonFTC.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Entity(tableName = "pit_scout")
public class PitScout {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "pit_scout_key")
    private String pitScoutKey;

    @NonNull
    @ColumnInfo(name = "event_key")
    private String eventKey;

    @NonNull
    @ColumnInfo(name = "team_key")
    private String teamKey;

    @ColumnInfo(name = "synced")
    private boolean hasBeenSynced;

    @ColumnInfo(name = "has_autonomous")
    private boolean hasAutonomous;

    @ColumnInfo(name = "auto_hasPossibleStartingPositionNetZone")
    private boolean autoHasPossibleStartingPositionNetZone;

    @ColumnInfo(name = "auto_hasPossibleStartingPositionObservationZone")
    private boolean autoHasPossibleStartingPositionObservationZone;

    @ColumnInfo(name="auto_preferred")
    private String autoPreferred;

    @ColumnInfo(name = "auto_points")
    private int autoPoints;

    @ColumnInfo(name="auto_prioritized")
    private String autoPrioritized;

    @ColumnInfo(name="teleOp_preferred_role")
    private String teleOpPreferredRole;

    @ColumnInfo(name = "teleOp_preferred_scoring")
    private String teleOpPreferredScoring;

    @ColumnInfo(name = "teleOp_points")
    private int teleOpPoints;

    @ColumnInfo(name = "end_hang_time")
    private int endHangTime;

    @ColumnInfo(name="end_hang")
    private String endHang;

    @ColumnInfo(name="robot_drive_train")
    private String robotDriveTrain;

    @ColumnInfo(name="robot_ingest")
    private String robotIngest;

    @ColumnInfo(name="robot_outgest")
    private String robotOutgest;

    @ColumnInfo(name = "extra_notes")
    private String extraNotes;
    
    @Ignore
    public PitScout(){
    }

    public PitScout(@NonNull String pitScoutKey, @NonNull String eventKey, @NonNull String teamKey, boolean hasBeenSynced,
                    boolean hasAutonomous, boolean autoHasPossibleStartingPositionNetZone, boolean autoHasPossibleStartingPositionObservationZone,
                    String autoPreferred, int autoPoints, String autoPrioritized,
                    String teleOpPreferredRole, String teleOpPreferredScoring, int teleOpPoints,
                    int endHangTime, String endHang,
                    @NonNull String robotDriveTrain, @NonNull String robotIngest, @NonNull String robotOutgest, @NonNull String extraNotes) {
        if(StringUtils.isBlank(pitScoutKey)){
            pitScoutKey = UUID.randomUUID().toString();
        }

        this.pitScoutKey = pitScoutKey;
        this.eventKey = eventKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;
        this.hasAutonomous = hasAutonomous;
        this.autoHasPossibleStartingPositionNetZone = autoHasPossibleStartingPositionNetZone;
        this.autoHasPossibleStartingPositionObservationZone = autoHasPossibleStartingPositionObservationZone;
        this.autoPreferred = autoPreferred;
        this.autoPoints = autoPoints;
        this.autoPrioritized = autoPrioritized;

        this.teleOpPreferredRole = teleOpPreferredRole;
        this.teleOpPreferredScoring = teleOpPreferredScoring;
        this.teleOpPoints = teleOpPoints;


        this.endHangTime = endHangTime;
        this.endHang = endHang;

        this.robotDriveTrain = robotDriveTrain;
        this.robotIngest = robotIngest;
        this.robotOutgest = robotOutgest;
        this.extraNotes = extraNotes;
    }

    @NonNull
    public String getPitScoutKey() {
        return pitScoutKey;
    }

    public void setPitScoutKey(@NonNull String pitScoutKey) {
        this.pitScoutKey = pitScoutKey;
    }

    @NonNull
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(@NonNull String eventKey) {
        this.eventKey = eventKey;
    }

    @NonNull
    public String getTeamKey() {
        return teamKey;
    }

    public void setTeamKey(@NonNull String teamKey) {
        this.teamKey = teamKey;
    }

    public boolean getHasBeenSynced() {
        return hasBeenSynced;
    }

    public void setHasBeenSynced(boolean hasBeenSynced) {
        this.hasBeenSynced = hasBeenSynced;
    }

    public boolean getHasAutonomous() {
        return hasAutonomous;
    }

    public void setHasAutonomous(boolean hasAutonomous) {
        this.hasAutonomous = hasAutonomous;
    }

    public boolean getAutoHasPossibleStartingPositionNetZone(){ return autoHasPossibleStartingPositionNetZone; }
    public void setAutoHasPossibleStartingPositionNetZone(boolean autoHasPossibleStartingPositionNetZone){ this.autoHasPossibleStartingPositionNetZone = autoHasPossibleStartingPositionNetZone; }

    public boolean getAutoHasPossibleStartingPositionObservationZone(){ return autoHasPossibleStartingPositionObservationZone; }
    public void setAutoHasPossibleStartingPositionObservationZone(boolean autoHasPossibleStartingPositionObservationZone){ this.autoHasPossibleStartingPositionObservationZone = autoHasPossibleStartingPositionObservationZone; }

    public String getAutoPreferred(){ return autoPreferred; }
    public void setAutoPreferred(String autoPreferred){ this.autoPreferred = autoPreferred; }

    public int getAutoPoints(){ return autoPoints; }
    public void setAutoPoints(int autoPoints){ this.autoPoints = autoPoints; }

    public String getAutoPrioritized(){ return autoPrioritized; }
    public void setAutoPrioritized(String autoPrioritized){ this.autoPrioritized = autoPrioritized; }


    public String getTeleOpPreferredRole(){ return teleOpPreferredRole; }
    public void setTeleOpPreferredRole(String teleOpPreferredRole){ this.teleOpPreferredRole = teleOpPreferredRole; }

    public String getTeleOpPreferredScoring(){ return teleOpPreferredScoring; }
    public void setTeleOpPreferredScoring(String teleOpPreferredScoring){ this.teleOpPreferredScoring = teleOpPreferredScoring; }

    public int getTeleOpPoints(){ return teleOpPoints; }
    public void setTeleOpPoints(int teleOpPoints){ this.teleOpPoints = teleOpPoints; }


    public int getEndHangTime() {
        return endHangTime;
    }

    public void setEndHangTime(int endHangTime) {
        this.endHangTime = endHangTime;
    }

    public String getEndHang() {
        return endHang;
    }

    public void setEndHang(String endHang) {
        this.endHang = endHang;
    }

    @NonNull
    public String getRobotDriveTrain() {
        return robotDriveTrain;
    }
    public void setRobotDriveTrain(@NonNull String robotDriveTrain) {
        this.robotDriveTrain = robotDriveTrain;
    }

    @NonNull
    public String getRobotIngest() {
        return robotIngest;
    }
    public void setRobotIngest(@NonNull String robotIngest) {
        this.robotIngest = robotIngest;
    }

    @NonNull
    public String getRobotOutgest() {
        return robotOutgest;
    }
    public void setRobotOutgest(@NonNull String robotOutgest) {
        this.robotOutgest = robotOutgest;
    }

    @NonNull
    public String getExtraNotes() {
        return extraNotes;
    }

    public void setExtraNotes(@NonNull String extraNotes) {
        this.extraNotes = extraNotes;
    }
}
