package org.hartlandrobotics.echelonFTC.orangeAlliance.fragments;

import org.hartlandrobotics.echelonFTC.database.entities.Evt;

public class EventsListViewModel {
    private String name;
    private String eventId;
    private String code;
    private boolean isSelected;

    public EventsListViewModel(Evt event) {
        this.name = event.getName();
        this.eventId = event.getEventId();
        this.code = event.getCode();
        this.isSelected = false;
    }

    public String getName() {
        return name;
    }

    public String getEventId() {
        return eventId;
    }

    public String getCode(){ return code; }


    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }
}
