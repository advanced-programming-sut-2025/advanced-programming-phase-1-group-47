package models;

import models.enums.Season;
import models.enums.month;

public class Time {
    private int hour;
    private Season season;
    private month month;
    private int day;

    public Time() {
        this.hour = 9;
        this.day = 1;
        this.season = Season.SPRING;
        this.month = models.enums.month.January; // مقداردهی پیش‌فرض به ماه
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public month getMonth() {
        return month;
    }

    public void setMonth(month month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String save() {
        return season + "," + month + "," + day + "," + hour;
    }

    public void load(String data) {
        String[] parts = data.split(",");
        if (parts.length != 4) return;
        this.season = Season.valueOf(parts[0]);
        this.month = month.valueOf(parts[1]);
        this.day = Integer.parseInt(parts[2]);
        this.hour = Integer.parseInt(parts[3]);
    }
}
