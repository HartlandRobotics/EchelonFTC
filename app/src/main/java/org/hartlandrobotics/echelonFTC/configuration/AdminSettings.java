package org.hartlandrobotics.echelonFTC.configuration;

import org.apache.commons.lang3.StringUtils;

public class AdminSettings {
    private String orangeAllianceApiKey;
    private String ftcApiKey;
    private String scoutingSeason;
    private String deviceRole;
    private String teamNumber;

    public AdminSettings(){};

    public AdminSettings(String orangeAllianceApiKey, String ftcApiKey, String scoutingSeason, String deviceRole, String teamNumber) {
        this.orangeAllianceApiKey = defaultString(orangeAllianceApiKey);
        this.ftcApiKey = defaultString(ftcApiKey);
        this.scoutingSeason = defaultString(scoutingSeason);
        this.deviceRole = StringUtils.defaultIfBlank(deviceRole, "red1");
        this.teamNumber = teamNumber;
    }

    public String getOrangeAllianceApiKey() { return defaultString(orangeAllianceApiKey); }
    public void setOrangeAllianceApiKey(String apiKey) {
        this.orangeAllianceApiKey = defaultString(apiKey);
    }

    public String getFtcApiKey() { return defaultString(ftcApiKey); }
    public void setFtcApiKey(String ftcApiKey) {
        this.ftcApiKey = defaultString(ftcApiKey);
    }

    public String getScoutingSeason(){ return scoutingSeason; }
    public void setScoutingSeason(String scoutingSeason){
        this.scoutingSeason = defaultString(scoutingSeason);
    }

    public String getDeviceRole() { return deviceRole; }
    public void setDeviceRole(String deviceRole){
        this.deviceRole = StringUtils.defaultIfBlank(deviceRole, "red1");
    }

    public String getTeamNumber() { return teamNumber; }
    public void setTeamNumber(String teamNumber) { this.teamNumber = teamNumber; }

    private String defaultString(String source ){
        return StringUtils.defaultIfBlank(source, StringUtils.EMPTY);
    }
}
