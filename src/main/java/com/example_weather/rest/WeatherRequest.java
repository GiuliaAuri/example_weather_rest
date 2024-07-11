package com.example_weather.rest;

import com.example_weather.model.GeoCoordinate;
import com.example_weather.model.WeatherData;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.HttpUrl;

import java.net.URL;

import static com.example_weather.rest.GeoCoordinateRequest.getGeoCoordinate;

public class WeatherRequest extends  RequestMaker{


    //https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=75c0bdc915749108790c7c447dd77bc9
    String protocol="https";
    String host="api.openweathermap.org";
    String path="data/2.5/weather";
    GeoCoordinate geocoordinate;
    WeatherData weatherData;
    static Dotenv dotenv = Dotenv.configure()
            .directory("./.env")
            .load();
    static String APIKEY = dotenv.get("API_KEY");
    public static WeatherData getWeatherData(String city){
        GeoCoordinate geocoordinate=getGeoCoordinate(city);
        System.out.println("DEBUG-"+geocoordinate);

        /*HttpUrl.Builder urlBuilder = new HttpUrl.Builder();

        urlBuilder.scheme(protocol);
        urlBuilder.host(host);
        urlBuilder.addPathSegment(path);*/


        return null;
    }

}
