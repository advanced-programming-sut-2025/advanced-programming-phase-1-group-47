package models;

import models.enums.Season;
import models.enums.weekDays;

//هندل کردن تایم هایی که خودکار جابه جا میشوند مثل ده شب تا 9 صبح و همچنین 28 روز بودن فصول به بخش گیم سپرده شده
public class Time {

    public static int hour = 9;

    public static void setHour(int hour) {
        Time.hour = hour;
    }

    public Season getSeason() {
        return Season.values()[((((((hour + 23) / 24) + 27) / 28) - 1) % 4)];
    }

    public weekDays getDayWeek(){
        return (weekDays.values()[((((hour + 23) / 24) - 1) % 7)]);
    }

    public int getDayOfSeason() {
        return (((hour + 23) / 24) % 28);
    }

    public int getHourOfDay() {
        return (hour % 24);
    }

    public static int getHour() {
        return hour;
    }

}