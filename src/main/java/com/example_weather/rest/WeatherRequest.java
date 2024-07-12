package com.example_weather.rest;

import com.example_weather.model.GeoCoordinate;
import com.example_weather.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;


public class WeatherRequest extends  RequestMaker{


    //https://api.openweathermap.org/data/2.5/weather?lat=44.34&lon=10.99&appid=75c0bdc915749108790c7c447dd77bc9
    String protocol="https";
    String host="api.openweathermap.org";
    String path="data/2.5/weather";
    GeoCoordinate geocoordinate;
    Dotenv dotenv = Dotenv.configure()
            .directory("./.env")
            .load();
    String APIKEY = dotenv.get("API_KEY");
    public WeatherData getWeatherData(String city){
        GeoCoordinateRequest geoCoordinateRequest= new GeoCoordinateRequest();
        GeoCoordinate geoCoordinate=geoCoordinateRequest.getGeoCoordinate(city);
        //controllo che le coordinate siano valide
        System.out.println("DEBUG-"+geocoordinate);

        HttpUrl.Builder urlBuilder = new HttpUrl.Builder();

        urlBuilder.scheme(protocol);
        urlBuilder.host(host);
        urlBuilder.addPathSegment(path)
                .addQueryParameter("lat", String.valueOf(geoCoordinate.getLatitude()))
                .addQueryParameter("lon", String.valueOf(geoCoordinate.getLongitude()))
                .addQueryParameter("apiKey", APIKEY);

        URL myurl=urlBuilder.build().url();
        Request request = new Request.Builder()
                .url(myurl)
                .build();


        Call call = client.newCall(request);
        try (Response response = call.execute()) {

            if (!response.isSuccessful()) {
                throw new RuntimeException("Unsuccessful response: code = " + response.code());
            }
            System.out.println("DEBUG"+response.toString());
            ResponseBody responseBody = response.body();
            JsonNode bodyNode = mapper.readTree(responseBody.string());

            System.out.println("DEBUG"+bodyNode.toString());

            WeatherData weatherData=new WeatherData();

            weatherData.setCity(city);
            weatherData.setDate(LocalDate.now());
            weatherData.setDescription(bodyNode.get("weather").get(0).get("description").toString());
            weatherData.setHumidity(bodyNode.get("main").get("humidity").asInt());
            weatherData.setWindSpeed(bodyNode.get("wind").get("speed").asDouble());
            weatherData.setTemperature(bodyNode.get("main").get("temp").asInt());


            return weatherData;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
