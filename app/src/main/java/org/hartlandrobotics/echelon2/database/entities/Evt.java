package org.hartlandrobotics.echelon2.database.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="event")
public class Evt {
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
    private String eventTypeKey

    @ColumnInfo(name = "division_key")
    private int divisionKey;

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
    private int dateSource;

    public Evt(@NonNull String eventKey, String seasonKey, String regionKey, String leagueKey, String eventCode, String eventTypeKey, int divisionKey, String divisionName, String eventName, String startDate, String endDate, String weekKey, String city, String state_prov, String country, String venue, String website, ) {
        this.eventKey = eventKey;
        this.seasonKey = seasonKey
        this.eventCode = eventCode;
        this.eventType = eventType;
        this.city = city;
        this.state = state;
        this.country = country;
        this.startDate = startDate;
        this.endDate = endDate;
        this.year = year;
        this.shortName = shortName;
        this.eventTypeString = eventTypeString;
        this.week = week;
        this.address = address;
        this.postalCode = postalCode;
        this.googleMapsPlaceId = googleMapsPlaceId;
        this.googleMapsUrl = googleMapsUrl;
        this.locationName = locationName;
        this.timezone = timezone;
        this.website = website;
        this.firstEventId = firstEventId;
        this.firstEventCode = firstEventCode;
    }

    @NonNull
    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(@NonNull String eventKey) {
        this.eventKey = eventKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getEventTypeString() {
        return eventTypeString;
    }

    public void setEventTypeString(String eventTypeString) {
        this.eventTypeString = eventTypeString;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getGoogleMapsPlaceId() {
        return googleMapsPlaceId;
    }

    public void setGoogleMapsPlaceId(String googleMapsPlaceId) {
        this.googleMapsPlaceId = googleMapsPlaceId;
    }

    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public void setGoogleMapsUrl(String googleMapsUrl) {
        this.googleMapsUrl = googleMapsUrl;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFirstEventId() {
        return firstEventId;
    }

    public void setFirstEventId(String firstEventId) {
        this.firstEventId = firstEventId;
    }

    public String getFirstEventCode() {
        return firstEventCode;
    }

    public void setFirstEventCode(String firstEventCode) {
        this.firstEventCode = firstEventCode;
    }

    public DistrictEvtCrossRef toDistrictEvent( String districtKey ){
        DistrictEvtCrossRef crossRef = new DistrictEvtCrossRef(districtKey, getEventKey());
        return crossRef;
    }
}
