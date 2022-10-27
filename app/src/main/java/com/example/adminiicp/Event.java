package com.example.adminiicp;

import java.util.Map;

public class Event {
    public String id;
    public String title;
    public Map<String, String> createdTime;
    public String type;
    public int month, year, dayOfMonth, hourOfDay, minute;

    public Event(String id, String title, Map<String, String> createdTime, String type, int month, int year, int dayOfMonth, int hourOfDay, int minute) {
        this.id = id;
        this.title = title;
        this.createdTime = createdTime;
        this.type = type;
        this.month = month;
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
    }
}
