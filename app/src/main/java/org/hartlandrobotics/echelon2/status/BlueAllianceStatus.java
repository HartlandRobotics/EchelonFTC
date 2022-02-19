package org.hartlandrobotics.echelon2.status;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

public class BlueAllianceStatus {
    private final static String TAG = "BlueAllianceStatus";

    private final static String SEASON_KEY = "SeasonKey";
    private final static String YEAR_KEY = "YearKey";
    private final static String DISTRICT_KEY = "DistrictKey";
    private final static String EVENT_KEY = "EventKey";

    private Context appContext;
    private boolean online;
    private String season;
    private String year;
    private String districtKey;
    private String eventKey;

    public BlueAllianceStatus(
            Context appContext
    ){
        if( appContext == null){
            Log.e(TAG, "appContext must be provided to BlueAllianceStatus");
            throw new IllegalArgumentException("appContext cannot be null");
        }

        this.appContext = appContext;
        this.online = false;
        loadSettingsFromPrefs();
    }

    public boolean getOnline(){ return online; }
    public void setOnline( boolean online){
        this.online = online;
    }

    public String getSeason(){return season;}
    public void setSeason(String season){
        this.season = season;
        setPreferenceValue(SEASON_KEY, season);
    }

    public String getYear(){ return year; };
    public void setYear(String year){
        this.year = year;
        setPreferenceValue(YEAR_KEY, year);
    }

    public String getDistrictKey() { return districtKey; }
    public void setDistrictKey( String districtKey ){
        this.districtKey = districtKey;
        setPreferenceValue(DISTRICT_KEY, districtKey);
    }

    public String getEventKey(){ return eventKey;}
    public void setEventKey( String eventKey){
        this.eventKey = eventKey;
        setPreferenceValue(EVENT_KEY, eventKey);
    }

    public void loadSettingsFromPrefs(){
        Log.i(TAG, "Loading BlueAllianceStatus from preferences");

        this.season = getSharedPreferences().getString(SEASON_KEY, StringUtils.EMPTY);
        this.year = getSharedPreferences().getString( YEAR_KEY, StringUtils.EMPTY);
        this.districtKey = getSharedPreferences().getString(DISTRICT_KEY, StringUtils.EMPTY);
        this.eventKey = getSharedPreferences().getString(EVENT_KEY, StringUtils.EMPTY );
    }

    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        return preferences;
    }

    private void setPreferenceValue(String key, String value){
        getSharedPreferences().edit().putString(key, value).apply();;
    }
}
