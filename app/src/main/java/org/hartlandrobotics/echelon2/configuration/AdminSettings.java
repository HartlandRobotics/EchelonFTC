package org.hartlandrobotics.echelon2.configuration;

import org.apache.commons.lang3.StringUtils;

public class AdminSettings {
    private String blueAllianceApiKey;
    private String scoutingSeason;

    public AdminSettings(){};
    public AdminSettings(String blueAllianceApiKey, String scoutingSeason) {
        this.blueAllianceApiKey = defaultString(blueAllianceApiKey);
        this.scoutingSeason = defaultString(scoutingSeason);
    }

    public String getBlueAllianceApiKey() { return defaultString(blueAllianceApiKey); }
    public void setBlueAllianceApiKey(String blueAllianceApiKey) {
        this.blueAllianceApiKey = defaultString(blueAllianceApiKey);
    }

    public String getScoutingSeason(){ return scoutingSeason; }
    public void setScoutingSeason(String scoutingSeason){
        this.scoutingSeason = defaultString(scoutingSeason);
    }

    private String defaultString( String source ){
        return StringUtils.defaultIfBlank(source, StringUtils.EMPTY);
    }
}
