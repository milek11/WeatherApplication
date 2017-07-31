package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import models.WeatherInfo;
import models.services.IWeatherObserver;
import models.services.WeatherService;


import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable, IWeatherObserver {


    @FXML
    TextField textFieldCity;
    @FXML
    Button buttonShowWeather;
    @FXML
    Label labelCityName;
    @FXML
    Label labelTemperature;
    @FXML
    Label labelDescription;
    @FXML
    ImageView imageWeather;
    @FXML
    ImageView imageSmallIcons;
    @FXML
    ImageView imageArrow;
    @FXML
    Label labelHumidity;
    @FXML
    Label labelSunrise;
    @FXML
    Label labelSunset;
    @FXML
    Label labelWind;
    @FXML
    Label labelWindDirection;
    @FXML
    Label labelTime;
    @FXML
    Label labelDate;
    @FXML
    ProgressIndicator progressIndicator;




    private WeatherService weatherService = WeatherService.getOurService();

    public void initialize(URL location, ResourceBundle resources) {
        weatherService.registerObserver(this);
        buttonShowWeather.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                getMakeCallAndProprties();
                progressIndicator.setVisible(true);
            }
        });
        textFieldCity.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    getMakeCallAndProprties();
                }
            }
        });
    }

    public void getMakeCallAndProprties() {
        try {
            weatherService.makeCall(textFieldCity.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        textFieldCity.clear();
    }

    public void onWeatherUpdate(WeatherInfo info) {

        System.out.println("Pogoda zaaktualizowana");
        labelCityName.setText(info.getCityName());
        labelTemperature.setText(""+(info.getTemperature()-273+"°"));
        labelDescription.setText(info.getWeatherDescription().substring(0,1).toUpperCase()+info.getWeatherDescription().substring(1));
        WeatherInfo weatherInfo = new WeatherInfo();
        imageWeather.setImage(new Image(weatherInfo.iconWeatherMap(info.getIconWeather())));
        labelHumidity.setText(""+info.getHumidity()+" %");
        labelSunrise.setText(""+convertDate(info.getSunrise()));
        labelSunset.setText(""+convertDate(info.getSunset()));
        labelWind.setText(""+info.getWindSpeed()+" m/s");
        labelWindDirection.setText(convertWindDirectionToString(info.getWindDirection()));
        imageArrow.setRotate(info.getWindDirection() + 180);
        labelTime.setText(getTimeOrRDate("HH:mm"));
        labelDate.setText(getTimeOrRDate("dd.MM"));
        imageArrow.setVisible(true);
        imageSmallIcons.setVisible(true);
        progressIndicator.setVisible(false);
    }

    public String convertDate(int date) {
        return new SimpleDateFormat("HH:mm").format(date * 1000L);
    }

    public String convertWindDirectionToString(int windDirection) {
        String[] windDirectionArray = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        int var = (int) ((windDirection / 22.5) + 0.5);
        imageArrow.setVisible(true);
        return windDirectionArray[var % 16];
    }

   public String getTimeOrRDate(String dateFormatPattern){
        DateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
        Date date = new Date();
        return dateFormat.format(date);
    }
}
