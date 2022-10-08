package com.example.adminiicp;

public class PollEvent {
    public String pollTitle;
    public String option1;
    public String option2;
    public String option3;
    public int option1_count;
    public int option2_count;
    public int option3_count;
    public int month, year, dayOfMonth, hourOfDay, minute;

    public PollEvent(String pollTitle, String option1, String option2, String option3, int month, int year, int dayOfMonth, int hourOfDay, int minute) {
        this.pollTitle = pollTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.month = month;
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.option1_count = 0;
        this.option2_count = 0;
        this.option3_count = 0;
    }
}
