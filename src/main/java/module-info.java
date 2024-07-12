
module com.example_weather {
        requires javafx.controls;
        requires javafx.fxml;
        requires okhttp3;
        requires com.fasterxml.jackson.databind;
        requires io.github.cdimascio.dotenv.java;
        requires java.desktop;
        requires annotations;

        opens com.weather_app to javafx.fxml;
        exports com.weather_app;
}