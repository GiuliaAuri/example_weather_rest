package com.weather_app;

import com.weather_app.model.WeatherData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    public void onHelloButtonClick(ActionEvent actionEvent) {
    }
    public void initialize(){

    }
}