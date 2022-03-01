package org.hartlandrobotics.echelon2.configuration;

import org.apache.commons.lang3.StringUtils;

public class AdminSettings {
    private String blueAllianceApiKey;
    private String scoutingSeason;
    private String deviceRole;
    private String teamNumber;

    public AdminSettings(){};
    public AdminSettings(String blueAllianceApiKey, String scoutingSeason, String deviceRole, String teamNumber) {
        this.blueAllianceApiKey = defaultString(blueAllianceApiKey);
        this.scoutingSeason = defaultString(scoutingSeason);
        this.deviceRole = StringUtils.defaultIfBlank(deviceRole, "red1");
        this.teamNumber = teamNumber;
    }

    public String getBlueAllianceApiKey() { return defaultString(blueAllianceApiKey); }
    public void setBlueAllianceApiKey(String blueAllianceApiKey) {
        this.blueAllianceApiKey = defaultString(blueAllianceApiKey);
    }

    public String getScoutingSeason(){ return scoutingSeason; }
    public void setScoutingSeason(String scoutingSeason){
        this.scoutingSeason = defaultString(scoutingSeason);
    }

    public String getDeviceRole() { return deviceRole; }
    public void setDeviceRole(String deviceRole){
        this.deviceRole = defaultString(deviceRole);
    }

    public String getTeamNumber() { return teamNumber; }
    public void setTeamNumber(String teamNumber) { this.teamNumber = teamNumber; }

    private String defaultString(String source ){
        return StringUtils.defaultIfBlank(source, StringUtils.EMPTY);
    }
}
