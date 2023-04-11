package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="event")
public class Evt {


    public String getLeagueKey() {
        return leagueKey;
    }

    public void setLeagueKey(String leagueKey) {
        this.leagueKey = leagueKey;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventTypeKey() {
        return eventTypeKey;
    }

    public void setEventTypeKey(String eventTypeKey) {
        this.eventTypeKey = eventTypeKey;
    }

    public int getDivisionKey() {
        return divisionKey;
    }

    public void setDivisionKey(int divisionKey) {
        this.divisionKey = divisionKey;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getWeekKey() {
        return weekKey;
    }

    public void setWeekKey(String weekKey) {
        this.weekKey = weekKey;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState_prov() {
        return state_prov;
    }

    public void setState_prov(String state_prov) {
        this.state_prov = state_prov;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getData_source() {
        return data_source;
    }

    public void setDatas_ource(int data_source) {
        this.data_source = data_source;
    }

    @NonNull
    public String getEventKey() {
        return eventKey;
    }

    public String getSeasonKey() {
        return seasonKey;
    }

    public String getRegionKey() {
        return regionKey;
    }

    public String getDivisionName() {
        return divisionName;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="event_key")
    private String eventKey;

    @ColumnInfo(name = "season_key")
    private String seasonKey;

    @ColumnInfo(name = "region_key")
    private String regionKey;

    @ColumnInfo(name = "league_key")
    private String leagueKey;

    @ColumnInfo(name = "event_code")
    private String eventCode;

    @ColumnInfo(name = "event_type_key")
    private String eventTypeKey;

    @ColumnInfo(name = "division_key")
    private int divisionKey;

    @ColumnInfo(name = "division_name")
    private String divisionName;

    @ColumnInfo(name = "event_name")
    private String eventName;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "week_key")
    private String weekKey;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "state_prov")
    private String state_prov;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "venue")
    private String venue;

    @ColumnInfo(name = "website")
    private String website;

    @ColumnInfo(name = "time_zone")
    private String timezone;

    @ColumnInfo(name = "is_public")
    private boolean isPublic;

    @ColumnInfo(name = "data_source")
    private int data_source;

    public Evt(@NonNull String eventKey, String seasonKey, String regionKey, String leagueKey, String eventCode, String eventTypeKey, int divisionKey, String divisionName, String eventName, String startDate, String endDate, String weekKey, String city, String state_prov, String country, String venue, String website, String timezone, boolean isPublic, int data_source) {
        this.eventKey = eventKey;
        this.seasonKey = seasonKey;
        this.regionKey = regionKey;
        this.eventCode = eventCode;
        this.leagueKey = leagueKey;
        this.eventCode = eventCode;
        this.eventTypeKey = eventTypeKey;
        this.divisionKey = divisionKey;
        this.divisionName = divisionName;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.weekKey = weekKey;
        this.city = city;
        this.state_prov = state_prov;
        this.country = country;
        this.venue = venue;
        this.website = website;
        this.timezone = timezone;
        this.isPublic = isPublic;
        this.data_source = data_source;
    }



    public DistrictEvtCrossRef toDistrictEvent( String districtKey ){
        DistrictEvtCrossRef crossRef = new DistrictEvtCrossRef(districtKey,getEventCode());
        return crossRef;
    }
}
