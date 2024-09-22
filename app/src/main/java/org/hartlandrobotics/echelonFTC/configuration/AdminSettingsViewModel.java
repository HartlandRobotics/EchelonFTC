package org.hartlandrobotics.echelonFTC.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.apache.commons.lang3.StringUtils;

public class AdminSettingsViewModel extends AdminSettings {
    private final AdminSettings fileSettings;
    private final AdminSettings prefSettings;

    public AdminSettings getFileSettings(){return fileSettings;}
    public AdminSettings getPerfSettings(){return prefSettings;}

    public AdminSettingsViewModel( AdminSettings fileSettings, AdminSettings prefSettings){
        this.fileSettings = fileSettings;
        this.prefSettings = prefSettings;

        /*
        String prefBlueAllianceApiKey = prefSettings.getBlueAllianceApiKey();
        this.setBlueAllianceApiKey(StringUtils.defaultIfBlank(prefBlueAllianceApiKey, fileSettings.getBlueAllianceApiKey()));
        */

        String prefApiKey = prefSettings.getOrangeAllianceApiKey();
        this.setOrangeAllianceApiKey(StringUtils.defaultIfBlank(prefApiKey, fileSettings.getOrangeAllianceApiKey()));

        String prefScoutingSeason = prefSettings.getScoutingSeason();
        this.setScoutingSeason( StringUtils.isEmpty(prefScoutingSeason) ? fileSettings.getScoutingSeason() : prefScoutingSeason );

        String prefDeviceRole = prefSettings.getDeviceRole();
        this.setDeviceRole( StringUtils.defaultIfBlank(prefDeviceRole, fileSettings.getDeviceRole()));

        String prefTeamNum = prefSettings.getTeamNumber();
        this.setTeamNumber(StringUtils.defaultIfBlank(prefTeamNum, fileSettings.getTeamNumber()));
    }

    public boolean isApikeySynced(){
        if( fileSettings == null  && prefSettings == null ){ return true; }
        String fileSetting = fileSettings == null ? StringUtils.EMPTY : fileSettings.getOrangeAllianceApiKey();
        String prefSetting = prefSettings == null ? StringUtils.EMPTY : prefSettings.getOrangeAllianceApiKey();

        return fileSetting.equals( prefSetting );
    }

    public boolean isScoutingSeasonSynced(){
        if( fileSettings == null  && prefSettings == null ){ return true; }
        String fileSetting = fileSettings == null ? StringUtils.EMPTY : fileSettings.getScoutingSeason();
        String prefSetting = prefSettings == null ? StringUtils.EMPTY : prefSettings.getScoutingSeason();

        return fileSetting.equals( prefSetting );
    }

    public void setOrangeAllianceApiKey(Context appContext, String apiKey) {
        super.setOrangeAllianceApiKey(apiKey);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        preferences.edit().putString(AdminSettingsProvider.ORANGE_ALLIANCE_KEY, apiKey).apply();
    }

    public void setTeamNumber(Context appContext, String teamNumber) {
        super.setTeamNumber(teamNumber);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        preferences.edit().putString(AdminSettingsProvider.TEAM_NUMBER, teamNumber).apply();
    }

    public void setDeviceRole(Context appContext, String deviceRole) {
        super.setDeviceRole(deviceRole);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        preferences.edit().putString(AdminSettingsProvider.DEVICE_ROLE, deviceRole).apply();
    }
}
