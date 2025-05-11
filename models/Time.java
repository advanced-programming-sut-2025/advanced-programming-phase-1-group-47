package models;

import models.enums.Season;
import models.enums.weekDays;

import javax.crypto.SealedObject;

//هندل کردن تایم هایی که خودکار جابه جا میشوند مثل ده شب تا 9 صبح و همچنین 28 روز بودن فصول به بخش گیم سپرده شده
public class Time {

    public  int hour = 9;

    public  void setHour(int hour) {
        Time.hour = hour;
    }

    public  Season getSeason() {
        return Season.values()[(((((hour + 23) / 24) + 27) / 28) % 4) - 1];
    }

    public  weekDays getDayWeek(){
        return (weekDays.values()[(((hour + 23) / 24) % 7) - 1]);
    }

    public  int getDayOfSeason() {
        return (((hour + 23) / 24) % 28);
    }

    public  int getHourOfDay() {
        return (hour % 24);
    }

    public  int getHour() {
        return hour;
    }

}
