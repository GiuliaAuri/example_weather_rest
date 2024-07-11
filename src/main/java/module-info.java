module com.example_weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires annotations;
    requires io.github.cdimascio.dotenv.java;

    opens com.example_weather to javafx.fxml;

}