package com.StardewValley.model;

public class Weather {
    boolean thunder = false;
    Weather weather;

    public void setThunder(boolean thunder) {
        this.thunder = thunder;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public Weather getWeather() {
        return this.weather;
    }
    public boolean isThunder() {
        return this.thunder;
    }
}
