package org.hartlandrobotics.echelonFTC.ftcapi.fragments;

import org.hartlandrobotics.echelonFTC.database.entities.Evt;

public class EventViewModel {
    private boolean isSelected;
    private String eventKey;
    private String eventCode;
    private String eventName;
    private String regionKey;

    public EventViewModel(Evt event){
        this.eventKey = event.getEventKey();
        this.eventCode = event.getEventCode();
        this.eventName = event.getEventName();
        this.regionKey = event.getRegionKey();

    }

    public String getEventKey(){ return eventKey; }
    public String getEventCode(){ return eventCode; }
    public String getEventName(){ return eventName; }
    public String getRegionKey(){ return regionKey; }
    public boolean getIsSelected() {
        return isSelected;
    }
    public void setIsSelected( boolean isSelected ){
        this.isSelected = isSelected;
    }

}



/*


public class FtcApiEvent {







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


 */