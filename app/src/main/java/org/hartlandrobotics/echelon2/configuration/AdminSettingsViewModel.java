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

        String prefScoutingYear = prefSettings.getScoutingYear();
        this.setScoutingYear( StringUtils.isEmpty(prefScoutingYear) ? fileSettings.getScoutingYear() : prefScoutingYear );
    }

    public boolean isBlueAllianceApikeySynced(){
        if( fileSettings == null  && prefSettings == null ){ return true; }
        String fileSetting = fileSettings == null ? StringUtils.EMPTY : fileSettings.getBlueAllianceApiKey();
        String prefSetting = prefSettings == null ? StringUtils.EMPTY : prefSettings.getBlueAllianceApiKey();

        return fileSetting.equals( prefSetting );
    }

    public boolean isScoutingYearSynced(){
        if( fileSettings == null  && prefSettings == null ){ return true; }
        String fileSetting = fileSettings == null ? StringUtils.EMPTY : fileSettings.getScoutingYear();
        String prefSetting = prefSettings == null ? StringUtils.EMPTY : prefSettings.getScoutingYear();

        return fileSetting.equals( prefSetting );
    }

    @Override
    public void setBlueAllianceApiKey(String blueAllianceApiKey) {
        super.setBlueAllianceApiKey(blueAllianceApiKey);
    }
}
