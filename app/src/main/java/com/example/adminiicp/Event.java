package com.example.adminiicp;

import java.util.Map;

public class Event {
    public String id;
    public String title;
    public Map<String, String> createdTime;
    public String type;

    public Event(String id, String title, Map<String, String> createdTime, String type) {
        this.id = id;
        this.title = title;
        this.createdTime = createdTime;
        this.type = type;
    }
}
