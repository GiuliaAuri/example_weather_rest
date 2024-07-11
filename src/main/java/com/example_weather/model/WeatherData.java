package com.example_weather.model;

import java.util.Date;

public class WeatherData {
    String city;
    String description;
    int humidity;
    double windSpeed;
    Date date;
    int temperature;

    public WeatherData(String city, String description, int humidity, double windSpeed, Date date, int temperature) {
        this.city = city;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.date = date;
        this.temperature = temperature;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "city='" + city + '\'' +
                ", description='" + description + '\'' +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", date=" + date +
                ", temperature=" + temperature +
                '}';
    }
}
