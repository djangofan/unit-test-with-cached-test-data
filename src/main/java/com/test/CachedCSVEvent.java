package com.test;

import com.opencsv.bean.CsvBindByName;

public class CachedCSVEvent {

    @CsvBindByName(required = true)
    private String name;

    @CsvBindByName(required = true)
    private String location;

    @CsvBindByName(required = true)
    private String id;

    @CsvBindByName(required = true)
    private String created;

    public CachedCSVEvent(String created, String location, String name, String id) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.created = created;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Event: " + String.join(",", name, location, id, created);
    }

}
