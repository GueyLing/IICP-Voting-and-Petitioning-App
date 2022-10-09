package com.example.adminiicp;

public class ElectionEvent {
    public String electionTitle;
    public String president_candidate1;
    public String president_candidate2;
    public String vice_president_candidate1;
    public String vice_president_candidate2;
    public int p1_count, p2_count, vp1_count, vp2_count;
    public int month, year, dayOfMonth, hourOfDay, minute;

    public ElectionEvent(String electionTitle, String president_candidate1, String president_candidate2, String vice_president_candidate1, String vice_president_candidate2, int month, int year, int dayOfMonth, int hourOfDay, int minute) {
        this.electionTitle = electionTitle;
        this.president_candidate1 = president_candidate1;
        this.president_candidate2 = president_candidate2;
        this.vice_president_candidate1 = vice_president_candidate1;
        this.vice_president_candidate2 = vice_president_candidate2;
        this.month = month;
        this.year = year;
        this.dayOfMonth = dayOfMonth;
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.p1_count = 0;
        this.p2_count = 0;
        this.vp1_count = 0;
        this.vp2_count = 0;
    }
}
