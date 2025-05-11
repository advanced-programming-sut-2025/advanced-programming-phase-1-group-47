package models;

import models.enums.Season;
import models.enums.weekDays;


public class Time {

    public static int hour = 9;

    public static void setHour(int hour) {
        Time.hour = hour;
    }

    public static Season getSeason() {
        return Season.values()[(((((hour + 23) / 24) + 27) / 28) % 4) - 1];
    }

    public static weekDays getDayWeek(){
        return weekDays.values()[(((hour + 23) / 24) % 7) - 1];
    }

    public static int getDayOfSeason() {
        return(((hour + 23) / 24) % 28);
    }

    public static int getHourOfDay() {
        return (hour % 24);
    }
    public static int getHour() {
        return hour;
    }

}
