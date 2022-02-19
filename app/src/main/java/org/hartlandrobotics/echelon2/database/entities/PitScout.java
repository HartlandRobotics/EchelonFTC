package org.hartlandrobotics.echelon2.database.entities;

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

    @ColumnInfo(name = "help_creating_auto")
    private boolean helpCreatingAuto;

    @ColumnInfo(name = "coding_language")
    private String codingLanguage;

    @ColumnInfo(name = "shoots_in_auto")
    private boolean shootsInAuto;

    @ColumnInfo(name = "percent_auto_shots")
    private double percentAutoShots;

    @ColumnInfo(name = "balls_picked_or_shot_in_auto")
    private int ballsPickedOrShotInAuto;

    @ColumnInfo(name = "can_shoot")
    private boolean canShoot;

    @ColumnInfo(name = "shooting_accuracy")
    private double shootingAccuracy;

    @ColumnInfo(name = "preferred_goal")
    private String preferredGoal;

    @ColumnInfo(name = "can_play_defense")
    private boolean canPlayDefense;

    @ColumnInfo(name = "can_robot_hang")
    private boolean canRobotHang;

    @ColumnInfo(name = "highest_hang_bar")
    private int highestHangBar;

    @ColumnInfo(name = "hang_time")
    private int hangTime;

    @ColumnInfo(name = "preferred_hanging_spot")
    private String preferredHangingSpot;

    @ColumnInfo(name = "side_swing")
    private int sideSwing;

    @ColumnInfo(name = "driver_experience")
    private int driverExperience;

    @ColumnInfo(name = "operator_experience")
    private int operatorExperience;

    @ColumnInfo(name = "human_player_accuracy")
    private double humanPlayerAccuracy;

    @ColumnInfo(name = "extra_notes")
    private String extraNotes;
    
    @Ignore
    public PitScout(){

    }

    public PitScout(@NonNull String pitScoutKey, @NonNull String eventKey, @NonNull String teamKey, boolean hasBeenSynced, boolean hasAutonomous, boolean helpCreatingAuto, @NonNull String codingLanguage, boolean shootsInAuto, double percentAutoShots, int ballsPickedOrShotInAuto, boolean canShoot, double shootingAccuracy, @NonNull String preferredGoal, boolean canPlayDefense, boolean canRobotHang, int highestHangBar, int hangTime, @NonNull String preferredHangingSpot, int sideSwing, int driverExperience, int operatorExperience, double humanPlayerAccuracy, @NonNull String extraNotes) {
        if(StringUtils.isBlank(pitScoutKey)){
            pitScoutKey = UUID.randomUUID().toString();
        }

        this.pitScoutKey = pitScoutKey;
        this.eventKey = eventKey;
        this.teamKey = teamKey;
        this.hasBeenSynced = hasBeenSynced;
        this.hasAutonomous = hasAutonomous;
        this.helpCreatingAuto = helpCreatingAuto;
        this.codingLanguage = codingLanguage;
        this.shootsInAuto = shootsInAuto;
        this.percentAutoShots = percentAutoShots;
        this.ballsPickedOrShotInAuto = ballsPickedOrShotInAuto;
        this.canShoot = canShoot;
        this.shootingAccuracy = shootingAccuracy;
        this.preferredGoal = preferredGoal;
        this.canPlayDefense = canPlayDefense;
        this.canRobotHang = canRobotHang;
        this.highestHangBar = highestHangBar;
        this.hangTime = hangTime;
        this.preferredHangingSpot = preferredHangingSpot;
        this.sideSwing = sideSwing;
        this.driverExperience = driverExperience;
        this.operatorExperience = operatorExperience;
        this.humanPlayerAccuracy = humanPlayerAccuracy;
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

    public boolean getHelpCreatingAuto() {
        return helpCreatingAuto;
    }

    public void setHelpCreatingAuto(boolean helpCreatingAuto) {
        this.helpCreatingAuto = helpCreatingAuto;
    }

    @NonNull
    public String getCodingLanguage() {
        return codingLanguage;
    }

    public void setCodingLanguage(@NonNull String codingLanguage) {
        this.codingLanguage = codingLanguage;
    }

    public boolean getShootsInAuto() {
        return shootsInAuto;
    }

    public void setShootsInAuto(boolean shootsInAuto) {
        this.shootsInAuto = shootsInAuto;
    }

    public double getPercentAutoShots() {
        return percentAutoShots;
    }

    public void setPercentAutoShots(double percentAutoShots) {
        this.percentAutoShots = percentAutoShots;
    }

    public int getBallsPickedOrShotInAuto() {
        return ballsPickedOrShotInAuto;
    }

    public void setBallsPickedOrShotInAuto(int ballsPickedOrShotInAuto) {
        this.ballsPickedOrShotInAuto = ballsPickedOrShotInAuto;
    }

    public boolean getCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public double getShootingAccuracy() {
        return shootingAccuracy;
    }

    public void setShootingAccuracy(double shootingAccuracy) {
        this.shootingAccuracy = shootingAccuracy;
    }

    @NonNull
    public String getPreferredGoal() {
        return preferredGoal;
    }

    public void setPreferredGoal(@NonNull String preferredGoal) {
        this.preferredGoal = preferredGoal;
    }

    public boolean getCanPlayDefense() {
        return canPlayDefense;
    }

    public void setCanPlayDefense(boolean canPlayDefense) {
        this.canPlayDefense = canPlayDefense;
    }

    public boolean getCanRobotHang() {
        return canRobotHang;
    }

    public void setCanRobotHang(boolean canRobotHang) {
        this.canRobotHang = canRobotHang;
    }

    public int getHighestHangBar() {
        return highestHangBar;
    }

    public void setHighestHangBar(int highestHangBar) {
        this.highestHangBar = highestHangBar;
    }

    public int getHangTime() {
        return hangTime;
    }

    public void setHangTime(int hangTime) {
        this.hangTime = hangTime;
    }

    @NonNull
    public String getPreferredHangingSpot() {
        return preferredHangingSpot;
    }

    public void setPreferredHangingSpot(@NonNull String preferredHangingSpot) {
        this.preferredHangingSpot = preferredHangingSpot;
    }

    public int getSideSwing() {
        return sideSwing;
    }

    public void setSideSwing(int sideSwing) {
        this.sideSwing = sideSwing;
    }

    public int getDriverExperience() {
        return driverExperience;
    }

    public void setDriverExperience(int driverExperience) {
        this.driverExperience = driverExperience;
    }

    public int getOperatorExperience() {
        return operatorExperience;
    }

    public void setOperatorExperience(int operatorExperience) {
        this.operatorExperience = operatorExperience;
    }

    public double getHumanPlayerAccuracy() { return humanPlayerAccuracy; }
    public void setHumanPlayerAccuracy( double humanPlayerAccuracy ){
        this.humanPlayerAccuracy = humanPlayerAccuracy;
    }

    @NonNull
    public String getExtraNotes() {
        return extraNotes;
    }

    public void setExtraNotes(@NonNull String extraNotes) {
        this.extraNotes = extraNotes;
    }
}
