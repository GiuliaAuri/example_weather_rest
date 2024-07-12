package com.weather_app.rest;

import com.weather_app.model.GeoCoordinate;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;



public class GeoCoordinateRequest extends RequestMaker{
    //indirizzo
    // https://api.openweathermap.org/geo/1.0/direct?q=London&limit=1&appid=75c0bdc915749108790c7c447dd77bc9
    String protocol = "https";
    String host = "api.openweathermap.org";
    String path = "geo/1.0/direct";

    Dotenv dotenv = Dotenv.configure()
            .directory("./.env")
            .load();
    String APIKEY = dotenv.get("API_KEY");


    public GeoCoordinate getGeoCoordinate(String city) {

        URL myurl = new HttpUrl.Builder()
                .scheme(protocol)
                .host(host)
                .addPathSegments(path)
                .addQueryParameter("q", city.replace(' ', '+'))
                .addQueryParameter("limit", "1")
                .addQueryParameter("apiKey", APIKEY)
                .build().url();

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
            GeoCoordinate coordinates = new GeoCoordinate();


            if (bodyNode.isArray() && bodyNode.size() > 0) {
                JsonNode firstItem = bodyNode.get(0);
                JsonNode latNode = firstItem.get("lat");
                JsonNode lonNode = firstItem.get("lon");
                if (latNode != null && lonNode != null) {
                    coordinates.setLatitude(latNode.asDouble());
                    coordinates.setLongitude(lonNode.asDouble());
                } else {
                    coordinates.setLatitude(0.0);
                    coordinates.setLongitude(0.0);
                }
            } else {
                coordinates.setLatitude(0.0);
                coordinates.setLongitude(0.0);
            }

            return coordinates;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
