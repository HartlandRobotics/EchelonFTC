package org.hartlandrobotics.echelon2.orangeAlliance.fragments;

import org.hartlandrobotics.echelon2.database.entities.Evt;

public class EventsListViewModel {
    private String eventName;
    private String eventKey;
    private String eventStartDate;
    private boolean isSelected;

    public EventsListViewModel(Evt event) {
        this.eventName = event.getEventName();
        this.eventKey = event.getEventKey();
        this.eventStartDate = event.getStartDate();
        this.isSelected = false;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventKey() {
        return eventKey;
    }

    public int getYear() {
        try {
            return Integer.parseInt(eventStartDate.substring(0, 4));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }
}
