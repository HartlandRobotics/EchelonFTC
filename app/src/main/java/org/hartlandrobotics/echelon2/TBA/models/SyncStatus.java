package org.hartlandrobotics.echelon2.TBA.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SyncStatus {

    @JsonProperty("current_season")
    private int currentSeason;

    @JsonProperty("max_season")
    private int maxSeason;

    @JsonProperty("is_datafeed_down")
    private boolean isDatafeedDown;

    @JsonProperty("down_events")
    private String[] downEvents;

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    public int getMaxSeason() {
        return maxSeason;
    }

    public void setMaxSeason(int maxSeason) {
        this.maxSeason = maxSeason;
    }

    public boolean isDatafeedDown() {
        return isDatafeedDown;
    }

    public void setDatafeedDown(boolean datafeedDown) {
        isDatafeedDown = datafeedDown;
    }

    public String[] getDownEvents() {
        return downEvents;
    }

    public void setDownEvents(String[] downEvents) {
        this.downEvents = downEvents;
    }
}
