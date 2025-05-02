package controllers;

import models.Time;
import models.enums.Season;
import models.enums.month;

public class TimeController {
    private static final String[] daysOfWeek = {
            "Saturday", "Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday"
    };

    private final Time time;

    public TimeController(Time time) {
        this.time = time;
    }

    public void advanceTime(int hours) {
        int hour = time.getHour() + hours;
        while (hour >= 24) {
            hour -= 24;
            advanceDate(1);
        }
        time.setHour(hour);
    }
    public void advanceDate(int days) {
        int day = time.getDay() + days;
        month currentMonth = time.getMonth();
        Season currentSeason = time.getSeason();

        while (day > 28) {
            day -= 28;
            int nextMonthIndex = (currentMonth.ordinal() + 1) % 12;
            currentMonth = month.values()[nextMonthIndex];

            if (nextMonthIndex % 3 == 1) {
                int nextSeasonIndex = (currentSeason.ordinal() + 1) % 4;
                currentSeason = Season.values()[nextSeasonIndex];
            }
        }

        time.setDay(day);
        time.setMonth(currentMonth);
        time.setSeason(currentSeason);
        resetHour();
    }

    public void nextTurn() {
        advanceTime(1);
    }

    public void resetHour() {
        time.setHour(9);
    }

    public String getDayOfWeek() {
        int totalDays = (time.getSeason().ordinal() * 28) + (time.getDay() - 1);
        return daysOfWeek[totalDays % 7];
    }

    public String getTimeHour() {
        return String.format("%02d:00", time.getHour());
    }

    public String getDate() {
        return capitalize(time.getSeason().toString()) + " " +
                capitalize(time.getMonth().toString()) + " " + time.getDay();
    }

    public String getDateTime() {
        return getDate() + " " + getTimeHour();
    }

    private String capitalize(String input) {
        return input.charAt(0) + input.substring(1).toLowerCase();
    }

    public Time getTime() {
        return time;
    }
}
