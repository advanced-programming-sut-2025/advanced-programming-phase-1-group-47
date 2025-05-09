package models;

import models.enums.Season;
import models.enums.month;
import models.enums.weekDays;

import javax.crypto.SealedObject;

//هندل کردن تایم هایی که خودکار جابه جا میشوند مثل ده شب تا 9 صبح و همچنین 28 روز بودن فصول به بخش گیم سپرده شده
public class Time {
    public static int hour = 9;
    public static Season getSeason() {
        return Season.values()[(hour / (24 * 30)) % 4];
    }

    public static weekDays getDayWeek(){
        return weekDays.values()[hour % 7];
    }
    public static int getDayOfMonth() {
        return (hour / 24) % 30 + 1; // روز بین 1 تا 30
    }
    public static month getMonth(){
        return month.values()[(hour / (24 * 30)) % 12];
    }

    public static void setHour(int hour) {
        Time.hour = hour;
    }

    public static int getHour() {
        return hour;
    }
}
