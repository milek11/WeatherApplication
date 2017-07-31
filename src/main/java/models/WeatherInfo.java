package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeatherInfo {
    private String cityName;
    private int temperature;
    private int humidity;
    private int pressure;
    private double windSpeed;
    private int windDirection;
    private int clouds;
    private String weatherDescription;
    private int sunrise;
    private int sunset;
    private String iconWeather;

    public WeatherInfo() { }

    public WeatherInfo(String cityName, int temperature, int humidity, int pressure, double windSpeed, int windDirection,
                       int clouds, String weatherDescription, int sunrise, int sunset, String iconWeather) {
        this.cityName = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.clouds = clouds;
        this.weatherDescription = weatherDescription;
        this.windDirection = windDirection;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.iconWeather = iconWeather;

    }
    public String getCityName(){return cityName;}

    public int getTemperature() {
        return temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getWindDirection() { return windDirection; }

    public int getClouds() {
        return clouds;
    }

    public int getSunrise() { return sunrise; }

    public int getSunset() { return sunset; }

    public String getWeatherDescription() { return weatherDescription; }

    public String getIconWeather(){ return iconWeather;}

    public String iconWeatherMap( String iconName){
        Map<String, String> iconMap = new HashMap<>();
        iconMap.put("01", "ico_clearsky.png");
        iconMap.put("02", "ico_fewclouds.png");
        iconMap.put("03", "ico_shatclouds.png");
        iconMap.put("04", "ico_brokenclouds.png");
        iconMap.put("09", "ico_showerrain.png");
        iconMap.put("10", "ico_rain.png");
        iconMap.put("11", "ico_thunderstorm.png");
        iconMap.put("13", "ico_snow.png");
        iconMap.put("50", "ico_mist.png");

        return iconMap.get(iconName.substring(0,2));
    }
}
