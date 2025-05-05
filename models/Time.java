package models;

import models.enums.Season;
import models.enums.weekDays;

public class Time {
    private int hour;
    private int day;
    private Season season;
    private weekDays weekDay;

    public Time() {
        this.hour = 9;
        this.season = Season.SPRING;
        this.weekDay = weekDays.Monday;
        this.day = 1;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setWeekDay(weekDays weekDay) {
        this.weekDay = weekDay;
    }

    public int getHour() {
        return hour;
    }


    public Season getSeason() {
        return season;
    }

    public weekDays getWeekDay() {
        return weekDay;
    }
}
