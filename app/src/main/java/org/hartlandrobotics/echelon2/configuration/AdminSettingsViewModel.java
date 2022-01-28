package org.hartlandrobotics.echelon2.configuration;

import org.apache.commons.lang3.StringUtils;

public class AdminSettingsViewModel extends AdminSettings {
    private final AdminSettings fileSettings;
    private final AdminSettings prefSettings;

    public AdminSettings getFileSettings(){return fileSettings;}
    public AdminSettings getPerfSettings(){return prefSettings;}

    public AdminSettingsViewModel( AdminSettings fileSettings, AdminSettings prefSettings){
        this.fileSettings = fileSettings;
        this.prefSettings = prefSettings;

        String prefBlueAllianceApiKey = prefSettings.getBlueAllianceApiKey();
        this.setBlueAllianceApiKey(StringUtils.defaultIfBlank(prefBlueAllianceApiKey, fileSettings.getBlueAllianceApiKey()));

        String prefScoutingSeason = prefSettings.getScoutingSeason();
        this.setScoutingSeason( StringUtils.isEmpty(prefScoutingSeason) ? fileSettings.getScoutingSeason() : prefScoutingSeason );
    }

    public boolean isBlueAllianceApikeySynced(){
        if( fileSettings == null  && prefSettings == null ){ return true; }
        String fileSetting = fileSettings == null ? StringUtils.EMPTY : fileSettings.getBlueAllianceApiKey();
        String prefSetting = prefSettings == null ? StringUtils.EMPTY : prefSettings.getBlueAllianceApiKey();

        return fileSetting.equals( prefSetting );
    }

    public boolean isScoutingSeasonSynced(){
        if( fileSettings == null  && prefSettings == null ){ return true; }
        String fileSetting = fileSettings == null ? StringUtils.EMPTY : fileSettings.getScoutingSeason();
        String prefSetting = prefSettings == null ? StringUtils.EMPTY : prefSettings.getScoutingSeason();

        return fileSetting.equals( prefSetting );
    }

    @Override
    public void setBlueAllianceApiKey(String blueAllianceApiKey) {
        super.setBlueAllianceApiKey(blueAllianceApiKey);
    }
}
