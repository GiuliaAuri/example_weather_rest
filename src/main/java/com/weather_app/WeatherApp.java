package com.weather_app;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WeatherApp extends Application{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WeatherApp.class.getResource("/com/weather_app/weather-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1050, 625);
        //scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Weather Application");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setScene(scene);
        stage.show();
        //stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}
