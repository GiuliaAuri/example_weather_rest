package com.weather_app.model;


import java.time.LocalDateTime;


public class WeatherData {
    String city;
    String description;
    int humidity;
    double windSpeed;
    LocalDateTime datetime;
    double temperatureMin;
    double temperatureMax;
    String main;


    public WeatherData() {

    }

    public WeatherData(String city, String description, int humidity, double windSpeed, LocalDateTime datetime, double temperatureMin, double temperatureMax, String main) {
        this.city = city;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.datetime = datetime;
        this.temperatureMin = temperatureMin;
        this.temperatureMax = temperatureMax;
        this.main = main;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", datetime=" + datetime +
                ", temperatureMin=" + temperatureMin +
                ", temperatureMax=" + temperatureMax +
                ", main='" + main + '\'' +
                '}';
    }
}
