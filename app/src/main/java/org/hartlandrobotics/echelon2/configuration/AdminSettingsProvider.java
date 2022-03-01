package org.hartlandrobotics.echelon2.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;

public class AdminSettingsProvider {
    private static final String LOG_TAG = AdminSettingsProvider.class.getSimpleName();

    private static final String CONFIG_DIRECTORY = "configuration";
    private static final String CONFIG_FILE_NAME = "adminSettings.json";

    // Field names used in shared preference storage
    public static final String BLUE_ALLIANCE_KEY = "BlueAllianceKey";
    public static final String SCOUTING_YEAR = "ScoutingYear";
    public static final String TEAM_NUMBER = "TeamNumber";
    public static final String DEVICE_ROLE = "DeviceRole";

    public static AdminSettingsViewModel getAdminSettings(Context appContext){
        AdminSettings fileSettings = settingsFromFile(appContext);
        AdminSettings prefSettings = settingsFromPrefs(appContext,fileSettings);

        if( prefSettings == null || fileSettings == null ){
            return null;
        }

        return new AdminSettingsViewModel(fileSettings, prefSettings);
    }

    private static AdminSettings settingsFromFile(Context appContext){
        File configFile = ConfigFileUtility.getFile(appContext, CONFIG_DIRECTORY, CONFIG_FILE_NAME );
        if( configFile == null ) {
            Log.e(LOG_TAG, "Missing Config File");
            Toast.makeText(appContext, "Missing Config File", Toast.LENGTH_LONG).show();
            return null;
        }

        AdminSettings settings = null;
        try {
            ObjectMapper jsonReader = new ObjectMapper();
            settings = jsonReader.readValue(configFile, AdminSettings.class);
        }
        catch( JsonParseException parseException ){
            Log.e(LOG_TAG, "Invalid Config File " + parseException.getMessage());
            Toast.makeText(appContext, "Invalid Config File", Toast.LENGTH_LONG).show();
        } catch (JsonMappingException e) {
            Log.e(LOG_TAG, "Could not map config File");
            Toast.makeText(appContext, "Could not map config File", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Could not read config File");
            Toast.makeText(appContext, "Could not read config File", Toast.LENGTH_LONG).show();
        }

        return settings;
    }

    private static AdminSettings settingsFromPrefs(Context appContext, AdminSettings fileSettings){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);

        String blueAllianceKey = preferences.getString(BLUE_ALLIANCE_KEY, null);
        if(StringUtils.isEmpty(blueAllianceKey)){
        }

        String scoutingYear = preferences.getString(SCOUTING_YEAR, null);

        String deviceRole = preferences.getString(DEVICE_ROLE, null);

        String teamNumber = preferences.getString(TEAM_NUMBER, null);

        return new AdminSettings(blueAllianceKey, scoutingYear, deviceRole, teamNumber);
    }



}
