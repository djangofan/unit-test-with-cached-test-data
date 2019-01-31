package com.test;

import com.opencsv.bean.CsvBindByName;

public class CachedCSVEvent {

    @CsvBindByName
    private String name;

    @CsvBindByName(column = "location", required = true)
    private String location;

    @CsvBindByName(column = "eventId")
    private String eventId;

    @CsvBindByName
    private String creationTime;

    public CachedCSVEvent(String creationTime, String location, String name, String eventId) {
        this.name = name;
        this.location = location;
        this.eventId = eventId;
        this.creationTime = creationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public String toString() {
        return "Event: " + String.join(",", name, location, eventId, creationTime);
    }

}
