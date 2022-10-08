package com.example.adminiicp;

public class PetitionEvent {
    public String petitionTitle;
    public String description;
    public int petition_no;
    public int month, year, dayOfMonth, hourOfDay, minute;

    public PetitionEvent(String petitionTitle, String description, int month, int year, int dayOfMonth, int hourOfDay, int minute) {
        this.petitionTitle = petitionTitle;
        this.description = description;
        this.month = month;
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.petition_no = 0;
    }
}
