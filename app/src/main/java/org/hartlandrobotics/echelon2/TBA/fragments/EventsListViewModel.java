package org.hartlandrobotics.echelon2.TBA.fragments;

import org.hartlandrobotics.echelon2.database.entities.Evt;

public class EventsListViewModel {
    private String eventName;
    private String eventKey;
    private int year;
    private boolean isSelected;

    public EventsListViewModel(Evt event) {
        this.eventName = event.getName();
        this.eventKey = event.getEventKey();
        this.year = event.getYear();
        this.isSelected = false;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventKey() {
        return eventKey;
    }

    public int getYear() {
        return year;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }
}
