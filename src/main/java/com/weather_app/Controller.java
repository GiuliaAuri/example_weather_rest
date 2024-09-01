package com.weather_app;

import animatefx.animation.FadeIn;
import com.weather_app.model.WeatherData;
import com.weather_app.rest.WeatherRequest;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Objects;


public class Controller {

    @FXML
    private HBox resultBox;
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
    @FXML private ImageView iconWeather;
    private List<WeatherData> weatherDataList;
    private final ObservableList<WeatherData> weatherDataObservableList = FXCollections.observableArrayList();
    private final WeatherRequest weatherRequest = new WeatherRequest();

    public void onButtonSearchClick() {
        resultBox.setVisible(true);
        Platform.runLater(() -> new FadeIn(resultBox).setSpeed(0.2).play());


        try {
            if (!citySearch.getText().isEmpty()) {
                weatherDataList = weatherRequest.getWeatherData(citySearch.getText());
                loadData();
            }
        }
        catch (RuntimeException e){
            showError("Error in search", e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        resultBox.setVisible(false);
        TableWeather.setSelectionModel(null);
        weatherColumn.setCellValueFactory(new PropertyValueFactory<>("main"));
        weatherColumn.setCellFactory(column -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(loadWeatherIconTable(item));
                    imageView.setFitHeight(40);
                    imageView.setFitWidth(40);
                    setGraphic(imageView);
                }
            }
        });
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("datetime"));
        timeColumn.setCellFactory(column -> new TableCell<>() {
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


    }

    /**
     *Loads the current weather data
     */
    private void loadData() {
        if (weatherDataList != null && !weatherDataList.isEmpty()) {
            WeatherData currentWeather = weatherDataList.getFirst();
            resultBox.setVisible(true);
            cityLabel.setText(currentWeather.getCity().toUpperCase());
            temperatureLabel.setText(String.format("%.2f °C\n%.2f °C", currentWeather.getTemperatureMin(), currentWeather.getTemperatureMax()));
            descriptionLabel.setText(currentWeather.getDescription());
            humidityLabel.setText(String.format("%d%%", currentWeather.getHumidity()));
            windSpeedLabel.setText(String.format("%.2f km/h", currentWeather.getWindSpeed()));

            iconWeather.setImage(new Image(getClass().getResourceAsStream(getPathIcon(currentWeather.getMain()))));
            loadTableData();
            citySearch.clear();
        }
        else{
            showError("No data found", "No data was found for the city entered.");
        }

    }

    /**
     * Fills the table with the weather forecast for the next 24 hours
     */
    private void loadTableData(){

        TableWeather.setItems(getWeatherDataTable());
        TableWeather.getColumns().setAll(weatherColumn,timeColumn,temperatureMinColumn,temperatureMaxColumn);

    }

    /**
     * Prepares a list of weather data to be displayed in the TableView,
     * limiting the display to a maximum of 12 items (up to 36 hours).
     * @return An observable list of weather data to be displayed in
     * the TableView
     */
    ObservableList<WeatherData> getWeatherDataTable(){
        weatherDataObservableList.clear();
        for (int i = 1; i <  Math.min(weatherDataList.size(), 12); i++) {
            weatherDataObservableList.add(weatherDataList.get(i));

        }
        return weatherDataObservableList;
    }


    /**
     * Determines the corresponding icon path for the given weather condition
     * @param LoadWeatherIcon A string represents the weather
     * @return The path to the corresponding weather icon
     */
    private String getPathIcon(String LoadWeatherIcon)
    {

        return switch (LoadWeatherIcon.toLowerCase()) {
            case "clear" -> "/img/clear.png";
            case "clouds" -> "/img/clouds.png";
            case "drizzle" -> "/img/drizzle.png";
            case "mist" -> "/img/mist.webp";
            case "rain" -> "/img/rain.png";
            case "snow" -> "/img/snow.png";
            case "thunderstorm" -> "/img/thunderstorm.webp";
            default -> {
                System.out.println("Error no icon has been found");
                yield "/img/default.png";
            }
        };
    }

    /**
     * Loads the weather icon for display in the table
     * @param LoadWeatherIcon a string represents the weather
     * @return Image represents weather icon
     */
    private Image loadWeatherIconTable(String LoadWeatherIcon)
    {
        return new Image(getClass().getResourceAsStream(getPathIcon(LoadWeatherIcon)));
    }

    /**
     * Displays an error message to the user
     * @param message The error message to display
     * @param title The title of the error alert
    */
    private void showError(String title, String message)
    {
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}