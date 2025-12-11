package org.hartlandrobotics.echelonFTC.ftapi.status;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

public class ApiStatus {
    private final static String TAG = "ApiStatus";
    private final static String REGION_KEY = "RegionKey";
    private final static String EVENT_KEY = "EventKey";
    private final static String EVENT_CODE = "EventCode";


    private Context appContext;
    private boolean online;
    private String regionKey;
    private String eventKey;
    private String eventCode;



    public ApiStatus(Context appContext ){
        if( appContext == null){
            Log.e(TAG, "appContext must be provided to ApiStatus");
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

    public String getRegionKey() { return regionKey; }
    public void setRegionKey( String regionKey ){
        this.regionKey = regionKey;
        setPreferenceValue(REGION_KEY, regionKey);
    }

    public String getEventKey(){ return eventKey;}
    public void setEventKey( String eventKey){
        this.eventKey = eventKey;
        setPreferenceValue(EVENT_KEY, eventKey);
    }

    public String getEventCode(){ return eventCode;}
    public void setEventCode( String eventCode ){
        this.eventCode = eventCode;
        setPreferenceValue(EVENT_CODE, eventCode);
    }


    public void loadSettingsFromPrefs(){
        Log.i(TAG, "Loading BlueAllianceStatus from preferences");

        //this.season = getSharedPreferences().getString(SEASON_KEY, StringUtils.EMPTY);
        //this.year = getSharedPreferences().getString( YEAR_KEY, StringUtils.EMPTY);
        this.regionKey = getSharedPreferences().getString(REGION_KEY, StringUtils.EMPTY);
        this.eventKey = getSharedPreferences().getString(EVENT_KEY, StringUtils.EMPTY );
        this.eventCode = getSharedPreferences().getString(EVENT_CODE, StringUtils.EMPTY );

    }

    private SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
        return preferences;
    }

    private void setPreferenceValue(String key, String value){
        getSharedPreferences().edit().putString(key, value).apply();;
    }
}

/*
package org.hartlandrobotics.echelonFTC.status;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

public class OrangeAllianceStatus {

    private final static String SEASON_KEY = "SeasonKey";
    private final static String YEAR_KEY = "YearKey";

    private String season;
    private String year;


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







}


 */