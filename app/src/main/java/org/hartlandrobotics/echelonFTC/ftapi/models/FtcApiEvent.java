package org.hartlandrobotics.echelonFTC.ftapi.models;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.StringUtils;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsProvider;
import org.hartlandrobotics.echelonFTC.configuration.AdminSettingsViewModel;
import org.hartlandrobotics.echelonFTC.database.entities.Evt;

public class FtcApiEvent {
    @JsonProperty("eventId")
    private String eventId;

    @JsonProperty("code")
    private String code;

    @JsonProperty("name")
    private String name;

    @JsonProperty("regionCode")
    private String regionCode;

    public String getEventId() {
        return eventId;
    }
    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public String getRegionCode() {
        return regionCode;
    }


    public Evt toEvent(Context appContext, String season){
        return new Evt(
                                                getEventId(), //eventkey,
                                                season,
                                                getRegionCode(), //regionkey,
                                                StringUtils.EMPTY, //leaguekey,
                                                getCode(), //eventcode,
                                                StringUtils.EMPTY, //eventtypekey,
                                                0, //divisionkey
                                                StringUtils.EMPTY,//divisionname,
                                                getName(),
                                                StringUtils.EMPTY,//startDate,
                                                StringUtils.EMPTY,//enddate,
                                                StringUtils.EMPTY,//weekKey,
                                                StringUtils.EMPTY,//city,
                                                StringUtils.EMPTY,//state_prov,
                                                StringUtils.EMPTY,//country,
                                                StringUtils.EMPTY,//venue,
                                                StringUtils.EMPTY,//website,
                                                StringUtils.EMPTY,
                                        true, //ispublic,
                                                0//data_source
                                        );
    }
}
