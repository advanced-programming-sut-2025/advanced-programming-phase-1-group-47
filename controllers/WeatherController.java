package controllers;
import models.Point;
import models.Result;
import models.Weather;
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
