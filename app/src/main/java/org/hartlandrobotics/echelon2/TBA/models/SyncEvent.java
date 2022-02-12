package org.hartlandrobotics.echelon2.TBA.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelon2.database.entities.Evt;

public class SyncEvent {
    @JsonProperty("key")
    private String eventKey;

    @JsonProperty("name")
    private String name;

    @JsonProperty("event_code")
    private String eventCode;

    @JsonProperty("event_type")
    private int eventType;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state")
    private String state;

    @JsonProperty("country")
    private String country;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("year")
    private int year;

    @JsonProperty("short_name")
    private String shortName;

    @JsonProperty("event_type_string")
    private String eventTypeString;

    @JsonProperty("week")
    private int week;

    @JsonProperty("address")
    private String address;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("google_maps_place_id")
    private String googleMapsPlaceId;

    @JsonProperty("google_maps_url")
    private String googleMapsUrl;

    @JsonProperty("location_name")
    private String locationName;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("website")
    private String website;

    @JsonProperty("first_event_id")
    private String firstEventId;

    @JsonProperty("first_event_code")
    private String firstEventCode;

    public String getEventKey() {
        return eventKey;
    }

    public String getName() {
        return name;
    }

    public String getEventCode() {
        return eventCode;
    }

    public int getEventType() {
        return eventType;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getYear() {
        return year;
    }

    public String getShortName() {
        return shortName;
    }

    public String getEventTypeString() {
        return eventTypeString;
    }

    public int getWeek() {
        return week;
    }

    public String getAddress() {
        return address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getGoogleMapsPlaceId() {
        return googleMapsPlaceId;
    }

    public String getGoogleMapsUrl() {
        return googleMapsUrl;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getWebsite() {
        return website;
    }

    public String getFirstEventId() {
        return firstEventId;
    }

    public String getFirstEventCode() {
        return firstEventCode;
    }

    public Evt toEvent(){
        Evt event = new Evt(getEventKey(), getName(), getEventCode(), getEventType(), getCity(), getState(), getCountry(), getStartDate(), getEndDate(), getYear(), getShortName(), getEventTypeString(), getWeek(), getAddress(), getPostalCode(), getGoogleMapsPlaceId(), getGoogleMapsUrl(), getLocationName(), getTimezone(), getWebsite(), getFirstEventId(), getFirstEventCode());
        return event;
    }
}
