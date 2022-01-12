package org.hartlandrobotics.echelon2.configuration;

import org.apache.commons.lang3.StringUtils;

public class AdminSettings {
    private String blueAllianceApiKey;
    private String scoutingYear;

    public AdminSettings(){};
    public AdminSettings(String blueAllianceApiKey, String scoutingYear) {
        this.blueAllianceApiKey = defaultString(blueAllianceApiKey);
        this.scoutingYear = defaultString(scoutingYear);
    }

    public String getBlueAllianceApiKey() { return defaultString(blueAllianceApiKey); }
    public void setBlueAllianceApiKey(String blueAllianceApiKey) {
        this.blueAllianceApiKey = defaultString(blueAllianceApiKey);
    }

    public String getScoutingYear(){ return scoutingYear; }
    public void setScoutingYear(String scoutingYear){
        this.scoutingYear = defaultString( scoutingYear );
    }

    private String defaultString( String source ){
        return StringUtils.defaultIfBlank(source, StringUtils.EMPTY);
    }
}
