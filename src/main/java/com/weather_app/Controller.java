package com.weather_app;

import com.weather_app.model.WeatherData;
import com.weather_app.rest.WeatherRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    @FXML
    private TableView<WeatherData> TableWeather;
    @FXML private TableColumn<String, String> weatherColumn;
    @FXML private TableColumn<LocalDateTime, String> timeColumn;
    @FXML private TableColumn<Double, String> temperatureMinColumn;
    @FXML private TableColumn<Double, String> temperatureMaxColumn;

    @FXML private Label cityLabel;
    @FXML private Label latitudeLabel;
    @FXML private Label longitudeLabel;
    @FXML private Label temperatureLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label humidityLabel;
    @FXML private Label windSpeedLabel;
    @FXML private TextField citySearch;
    @FXML private Button buttonSearch;
    List<WeatherData> weatherDataList;
    WeatherRequest weatherRequest;
    public void onbuttonSearchClick() {
        if(!citySearch.getText().isEmpty()){
            weatherDataList=weatherRequest.getWeatherData(citySearch.getText());
            //
        }

    }
    public void initialize(){


    }
    public void DataLoad()
    {
       //cityLabel.setLabelFor(weatherDataList.get(0).getCity());
    }
}