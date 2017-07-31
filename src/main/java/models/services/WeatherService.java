package models.services;

import javafx.application.Platform;
import models.Config;
import models.WeatherInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherService {

    private static WeatherService ourService = new WeatherService(new HttpService());

    private WeatherService(HttpService httpService) {
        this.httpService = httpService;
        executorService = Executors.newFixedThreadPool(1);
    }

    public static WeatherService getOurService() {
        return ourService;
    }

    private List<IWeatherObserver> observerList = new ArrayList<>();
    private HttpService httpService;
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

    private ExecutorService executorService;

    public void makeCall(String city) throws IOException {
        parseJson(httpService.connectAndResponse(Config.APP_URL + "?q=" + city + "&appid=" + Config.APP_ID));
    }

    private void parseJson(String json) {
        JSONObject rootObject = new JSONObject(json);
        if (rootObject.getInt("cod") != 200) {
            System.out.println("Miasto nie iestnieje.");
            cityName = "Chose another";
            temperature = 0;
            humidity = 0;
            pressure = 0;
            clouds = 0;
            windSpeed = 0;
            windDirection = 0;
            sunrise=0;
            sunset=0;
            return;
        }
        //nameObject = rootObject.getString("name");
        JSONObject mainObject = rootObject.getJSONObject("main");
        JSONObject windObject = rootObject.getJSONObject("wind");
        JSONObject cloudsObject = rootObject.getJSONObject("clouds");
        JSONArray weatherArray = rootObject.getJSONArray("weather");
        JSONObject zeroObjectFromWeather = weatherArray.getJSONObject(0);
        JSONObject sunPosition = rootObject.getJSONObject("sys");

        cityName = rootObject.getString("name");
        temperature = (int) mainObject.getInt("temp");
        humidity = mainObject.getInt("humidity");
        pressure = mainObject.getInt("pressure");
        windSpeed = windObject.getDouble("speed");
        windDirection = windObject.getInt("deg");
        clouds = cloudsObject.getInt("all");
        weatherDescription = zeroObjectFromWeather.getString("description");
        sunrise = sunPosition.getInt("sunrise");
        sunset = sunPosition.getInt("sunset");
        iconWeather = zeroObjectFromWeather.getString("icon");

        Platform.runLater((new Runnable() {
            @Override
            public void run() {
                notifyObservers();
            }
        }));

    }

    // wzorzec Observer
    public void registerObserver(IWeatherObserver observer) {
        observerList.add(observer);
    }

    public void notifyObservers() {
        WeatherInfo weatherInfo = new WeatherInfo(cityName, temperature, humidity, pressure, windSpeed, windDirection,
                                                    clouds, weatherDescription, sunrise, sunset, iconWeather);
        for (IWeatherObserver iWO : observerList) {
            iWO.onWeatherUpdate(weatherInfo);
        }
    }
}
