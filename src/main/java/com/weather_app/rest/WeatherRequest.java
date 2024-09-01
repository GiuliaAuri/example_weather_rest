package com.weather_app.rest;

import com.weather_app.model.GeoCoordinate;
import com.weather_app.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class WeatherRequest extends  RequestMaker{

    String protocol="http";
    String host="api.openweathermap.org";
    String path="data/2.5/forecast";
    GeoCoordinate geocoordinate;
    Dotenv dotenv = Dotenv.configure()
            .directory("./.env")
            .load();
    String APIKEY = dotenv.get("API_KEY");

    /*
    *  Constructs the URL for the API request,
    * executes the request, and processes the response,
    * returning a list of weather data for the specified city
    * @param city The name of the city for which to retrieve weather data
    * @return List<WeatherData>  A list of WeatherData objects containing weather information for the specified city
    */
    public List<WeatherData> getWeatherData(String city){
        GeoCoordinateRequest geoCoordinateRequest= new GeoCoordinateRequest();
        GeoCoordinate geoCoordinate=geoCoordinateRequest.getGeoCoordinate(city);
        if(geoCoordinate.getLatitude()==0.0 || geoCoordinate.getLongitude()==0.0){
            throw new RuntimeException("Error geographic coordinates are not valid");
        }

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
            System.out.println("DEBUG"+response);
            ResponseBody responseBody = response.body();
            if(responseBody==null) {
                throw new RuntimeException("RespondeBody is null");
            }

            JsonNode bodyNode = mapper.readTree(responseBody.string());

            System.out.println("DEBUG"+bodyNode.toString());


            List<WeatherData> weatherDataList=new ArrayList<>();
            int i=0;
            for (JsonNode weatherNode: bodyNode.get("list")) {
                if(i>12)
                    break;
                WeatherData tmp = new WeatherData();
                tmp.setCity(geoCoordinate.getCity());
                tmp.setDescription(weatherNode.get("weather").get(0).get("description").asText());
                tmp.setTemperatureMin(weatherNode.get("main").get("temp_min").asDouble());
                tmp.setTemperatureMax(weatherNode.get("main").get("temp_max").asDouble());
                tmp.setMain(weatherNode.get("weather").get(0).get("main").asText());
                tmp.setWindSpeed(weatherNode.get("wind").get("speed").asDouble());
                tmp.setHumidity(weatherNode.get("main").get("humidity").asInt());
                tmp.setDatetime(StringToLocalDateTime(weatherNode.get("dt_txt").asText()));

                weatherDataList.add(tmp);
                System.out.println("DEBUG-"+i + tmp);
                i++;
            }

            return weatherDataList;
        } catch (IOException e) {
            throw new RuntimeException("Error in communication with the API: " + e.getMessage());
        }
    }
    /**
     * Converts a string representing date and time into a LocalDateTime object
     * according to the specified format
     * @param dateTimeString The string representing date and time
     * @return LocalDateTime
     */
    public LocalDateTime StringToLocalDateTime(String dateTimeString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);

        System.out.println("DEBUG - The date and time converted: " + dateTime);
        return dateTime;
    }

}
