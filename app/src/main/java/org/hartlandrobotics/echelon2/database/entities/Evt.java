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

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "event_code")
    private String eventCode;

    @ColumnInfo(name = "event_type")
    private int eventType;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "state")
    private String state;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "start_date")
    private String startDate;

    @ColumnInfo(name = "end_date")
    private String endDate;

    @ColumnInfo(name = "year")
    private int year;

    @ColumnInfo(name = "short_name")
    private String shortName;

    @ColumnInfo(name = "event_type_string")
    private String eventTypeString;

    @ColumnInfo(name = "week")
    private int week;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "postal_code")
    private String postalCode;

    @ColumnInfo(name = "google_maps_place_id")
    private String googleMapsPlaceId;

    @ColumnInfo(name = "google_maps_url")
    private String googleMapsUrl;

    @ColumnInfo(name = "location_name")
    private String locationName;

    @ColumnInfo(name = "timezone")
    private String timezone;

    @ColumnInfo(name = "website")
    private String website;

    @ColumnInfo(name = "first_event_id")
    private String firstEventId;

    @ColumnInfo(name = "first_event_code")
    private String firstEventCode;

    public Evt(@NonNull String eventKey, String name, String eventCode, int eventType, String city, String state, String country, String startDate, String endDate, int year, String shortName, String eventTypeString, int week, String address, String postalCode, String googleMapsPlaceId, String googleMapsUrl, String locationName, String timezone, String website, String firstEventId, String firstEventCode) {
        this.eventKey = eventKey;
        this.name = name;
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
}
