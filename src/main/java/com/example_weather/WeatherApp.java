package com.example_weather;

import com.example_weather.model.GeoCoordinate;
import com.example_weather.model.WeatherData;
import com.example_weather.rest.GeoCoordinateRequest;
import com.example_weather.rest.WeatherRequest;

public class WeatherApp {
    public static void main(String[] args){
        WeatherRequest weatherRequest=new WeatherRequest();
        weatherRequest.getWeatherData("Suzzara");
    }
}
