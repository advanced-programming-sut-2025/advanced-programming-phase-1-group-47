package com.StardewValley.controllers;

import com.StardewValley.model.Point;
import com.StardewValley.model.Result;
import com.StardewValley.model.Weather;

public class WeatherController {

    public Result<String> ThunderStrike(Point point) {
        return null;
    }
    public Result handleWeather(Weather weather) {
        if (weather == null) {
            return null;
        }
        if (weather.getWeather().isThunder()){
            return null;
        }
        return null;
    }
}
