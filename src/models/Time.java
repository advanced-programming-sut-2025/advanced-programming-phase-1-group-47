package models;

import models.buildings.Building;
import models.enums.Season;
import models.enums.weekDays;
import models.enums.month;

public class Time {
    private int hour;
    private Season season;
    private weekDays weekDay;
    private month month;
    public void setHour(int hour) {
        this.hour = hour;
    }
    public void setMonth(month month) {
        this.month = month;
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

    public month getMonth() {
        return month;
    }

    public Season getSeason() {
        return season;
    }

    public weekDays getWeekDay() {
        return weekDay;
    }
}
