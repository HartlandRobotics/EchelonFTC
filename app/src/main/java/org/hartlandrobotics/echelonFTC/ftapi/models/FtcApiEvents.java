package org.hartlandrobotics.echelonFTC.ftapi.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FtcApiEvents {
    @JsonProperty("events")
    private List<FtcApiEvent> events;
    @JsonProperty("eventCount")
    private int eventCount;

    public List<FtcApiEvent> getEvents() {
        return events;
    }
    public int getEventCount() {
        return eventCount;
    }


    /*

       public Evt toEvent(){
        //String eventKey, String seasonKey, String regionKey, String leagueKey, String eventCode, String eventTypeKey, int divisionKey, String divisionName, String eventName, String startDate, String endDate, String weekKey, String city, String state_prov, String country, String venue, String website, String timezone, boolean isPublic, int data_source

        Evt event = new Evt(getEventKey(), getSeasonKey(), getRegionKey(),getLeagueKey(), getEventCode(), getEventTypeKey(),getDivisionKey(),getDivisionName(),getEventName(),getStartDate(),getEndDate(),getWeekKey(),getCity(),getState_prov(), getCountry(), getVenue(),getWebsite(),getTimeZone(),isPublic(),getDataSource());
        return event;
    }


    events: [
    "eventId": "e899aed5-9e8f-2645-88d2-58ff6bddea89",
			"code": "AUADQ",
			"divisionCode": null,
			"name": "South Australia Qualifier",
			"remote": false,
			"hybrid": false,
			"fieldCount": 1,
			"published": false,
			"type": "2",
			"typeName": "Qualifier",
			"regionCode": "AU",
			"leagueCode": null,
			"districtCode": "",
			"venue": "The Student Robotics Club of South Australia Inc (the RoboRoos)",
			"address": "Clovelly Park",
			"city": "Adelaide",
			"stateprov": "SA",
			"country": "Australia",
			"website": null,
			"liveStreamUrl": null,
			"coordinates": null,
			"webcasts": null,
			"timezone": "Australia/Adelaide",
			"dateStart": "2025-11-15T00:00:00",
			"dateEnd": "2025-11-16T00:00:00"
     */
}


/*
package org.hartlandrobotics.echelonFTC.orangeAlliance.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hartlandrobotics.echelonFTC.database.entities.Evt;

public class SyncEvent {
    @JsonProperty("event_key")
    private String eventKey;

//E
    @JsonProperty("season_key")
    private String seasonKey;

    @JsonProperty("region_key")
    private String regionKey;

    @JsonProperty("league_key")
    private String leagueKey;

    @JsonProperty("event_code")
    private String eventCode;

    @JsonProperty("event_type_key")
    private String eventTypeKey;

    @JsonProperty("division_key")
    private int divisionKey;



    @JsonProperty("division_name")
    private String divisionName;

    @JsonProperty("event_name")
    private String eventName;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    @JsonProperty("week_key")
    private String weekKey;

    @JsonProperty("city")
    private String city;

    @JsonProperty("state_prov")
    private String state_prov;

    @JsonProperty("country")
    private String country;

    @JsonProperty("venue")
    private String venue;

    @JsonProperty("website")
    private String website;

    @JsonProperty("time_zone")
    private String timeZone;

    @JsonProperty("is_public")
    private boolean isPublic;

    @JsonProperty("date-source")
    private int dataSource;

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getSeasonKey() {
        return seasonKey;
    }

    public void setSeasonKey(String seasonKey) {
        this.seasonKey = seasonKey;
    }

    public String getRegionKey() {
        return regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

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

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getDataSource() {
        return dataSource;
    }

    public void setDataSource(int dataSource) {
        this.dataSource = dataSource;
    }




    public Evt toEvent(){
        //String eventKey, String seasonKey, String regionKey, String leagueKey, String eventCode, String eventTypeKey, int divisionKey, String divisionName, String eventName, String startDate, String endDate, String weekKey, String city, String state_prov, String country, String venue, String website, String timezone, boolean isPublic, int data_source

        Evt event = new Evt(getEventKey(), getSeasonKey(), getRegionKey(),getLeagueKey(), getEventCode(), getEventTypeKey(),getDivisionKey(),getDivisionName(),getEventName(),getStartDate(),getEndDate(),getWeekKey(),getCity(),getState_prov(), getCountry(), getVenue(),getWebsite(),getTimeZone(),isPublic(),getDataSource());
        return event;
    }
}

 */