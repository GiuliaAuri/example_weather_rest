package com.weather_app;

import com.weather_app.model.WeatherData;
import com.weather_app.rest.WeatherRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;


public class Controller {
    @FXML
    private TableView<WeatherData> TableWeather;
    @FXML private TableColumn<WeatherData, String> weatherColumn;
    @FXML private TableColumn<WeatherData, LocalDateTime> timeColumn;
    @FXML private TableColumn<WeatherData, Double> temperatureMinColumn;
    @FXML private TableColumn<WeatherData, Double> temperatureMaxColumn;

    @FXML private Label cityLabel;

    @FXML private Label temperatureLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label humidityLabel;
    @FXML private Label windSpeedLabel;
    @FXML private TextField citySearch;
    @FXML private Button buttonSearch;
    @FXML private ImageView iconWeather;
    private List<WeatherData> weatherDataList;
    private ObservableList<WeatherData> weatherDataObservableList = FXCollections.observableArrayList();
    private WeatherRequest weatherRequest = new WeatherRequest();

    public void onButtonSearchClick() {
        if (!citySearch.getText().isEmpty()) {
            weatherDataList = weatherRequest.getWeatherData(citySearch.getText());
            loadData();
        }
    }

    @FXML
    public void initialize() {
        weatherColumn.setCellValueFactory(new PropertyValueFactory<>("main")); // Rimanere con il campo main
        weatherColumn.setCellFactory(column -> new TableCell<WeatherData, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(loadWeatherIconTable(item)); // Carica l'icona basata sul valore
                    imageView.setFitHeight(30);
                    imageView.setFitWidth(30);
                    setGraphic(imageView);
                }
            }
        });
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        timeColumn.setCellFactory(column -> new TableCell<WeatherData, LocalDateTime>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, HH:mm");

            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });
        temperatureMinColumn.setCellValueFactory(new PropertyValueFactory<>("temperatureMin"));
        temperatureMaxColumn.setCellValueFactory(new PropertyValueFactory<>("temperatureMax"));
        weatherDataList = weatherRequest.getWeatherData("Modena");
        loadData();
    }

    /**
     * A method that loads current weather data
     */
    private void loadData() {
        if (weatherDataList != null && !weatherDataList.isEmpty()) {
            WeatherData currentWeather = weatherDataList.get(0);
            cityLabel.setText(currentWeather.getCity().toUpperCase());
            temperatureLabel.setText(String.format("%.2f °C\n%.2f °C", currentWeather.getTemperatureMin(), currentWeather.getTemperatureMax()));
            descriptionLabel.setText(currentWeather.getDescription());
            humidityLabel.setText(String.format("%d%%", currentWeather.getHumidity()));
            windSpeedLabel.setText(String.format("%.2f km/h", currentWeather.getWindSpeed()));

            loadWeatherIcon(currentWeather.getMain());
            loadTableData();
            citySearch.clear();
        }

    }

    /**
     * A method filling table containing forecast for next 24 hours
     */
    private void loadTableData(){

        TableWeather.setItems(getWeatherDataTable());
        TableWeather.getColumns().setAll(weatherColumn,timeColumn,temperatureMinColumn,temperatureMaxColumn);

    }

    /**
     * A method that prepares a list of weather data to be
     * displayed in TableView and limiting the display to a maximum
     * of 7 items.
     * @return an observable list of weather data to be displayed in
     * the TableView
     */
    ObservableList<WeatherData> getWeatherDataTable(){
        weatherDataObservableList.clear();
        for (int i = 0; i <  Math.min(weatherDataList.size(), 7); i++) {
            WeatherData currentWeather = weatherDataList.get(i);
            loadWeatherIcon(currentWeather.getMain());
            weatherDataObservableList.add(weatherDataList.get(i));

        }
        return weatherDataObservableList;
    }

    /**
     * A method that loads the icon of the weather
     * @param LoadWeatherIcon the weather that corresponds to the icon to be displayed
     */
    private void loadWeatherIcon(String LoadWeatherIcon)
    {
        iconWeather.setImage(new Image(getClass().getResourceAsStream(getPathIcon(LoadWeatherIcon))));
    }

    /**
     * A method that calculates the correspondence between weather and icon
     * @param LoadWeatherIcon represents the weather
     * @return the path of icon
     */
    private String getPathIcon(String LoadWeatherIcon)
    {
        String iconPath = "";

        switch (LoadWeatherIcon.toLowerCase()) {
            case "clear":
                iconPath = "/img/clear.png";
                break;
            case "clouds":
                iconPath = "/img/clouds.png";
                break;
            case "drizzle":
                iconPath="/img/drizzle.png";
                break;
            case "mist":
                iconPath="/img/mist.webp";
                break;
            case "rain":
                iconPath="/img/rain.png";
                break;
            case "snow":
                iconPath="/img/snow.png";
                break;
            case "thunderstorm":
                iconPath="/img/thunderstorm.webp";
                break;
            default:
                System.out.println("Error no icon has been found");
                break;
        }
        return iconPath;
    }

    /**
     * A method that loads the icon of the weather in the table
     * @param LoadWeatherIcon represents the weather
     * @return Image represents weather icon
     */
    private Image loadWeatherIconTable(String LoadWeatherIcon)
    {
        return new Image(getClass().getResourceAsStream(getPathIcon(LoadWeatherIcon)));
    }


}