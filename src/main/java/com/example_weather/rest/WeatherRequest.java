package com.example_weather.rest;

import com.example_weather.model.GeoCoordinate;
import com.example_weather.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class WeatherRequest extends  RequestMaker{


    //http://api.openweathermap.org/data/2.5/forecast?lat=44.34&lon=10.99&appid=75c0bdc915749108790c7c447dd77bc9
    String protocol="http";
    String host="api.openweathermap.org";
    String path="data/2.5/forecast";
    GeoCoordinate geocoordinate;
    Dotenv dotenv = Dotenv.configure()
            .directory("./.env")
            .load();
    String APIKEY = dotenv.get("API_KEY");
    public List<WeatherData> getWeatherData(String city){
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
                .addQueryParameter("units", "metric")
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


            List<WeatherData> weatherDataList=new ArrayList<>();
            int i=0;
            for (JsonNode weatherNode: bodyNode.get("list")) {
                if(i>7)
                    break;
                WeatherData tmp = new WeatherData();
                tmp.setCity(city);
                tmp.setDescription(weatherNode.get("weather").get(0).get("description").asText());
                tmp.setTemperatureMin(weatherNode.get("main").get("temp_min").asDouble());
                tmp.setTemperatureMax(weatherNode.get("main").get("temp_max").asDouble());
                tmp.setMain(weatherNode.get("weather").get(0).get("main").asText());
                tmp.setWindSpeed(weatherNode.get("wind").get("speed").asDouble());
                tmp.setHumidity(weatherNode.get("main").get("humidity").asInt());
                tmp.setDatetime(StringToLocalDateTime(weatherNode.get("dt_txt").asText()));

                weatherDataList.add(tmp);
                System.out.println("DEBUG-"+i + tmp.toString());
                i++;
            }

            return weatherDataList;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public LocalDateTime StringToLocalDateTime(String dateTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        System.out.println("DEBUG - La data e ora convertite: " + dateTime);
        return dateTime;
    }

}
