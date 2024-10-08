package com.weather_app.rest;

import com.weather_app.model.GeoCoordinate;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;



public class GeoCoordinateRequest extends RequestMaker{

    String protocol = "https";
    String host = "api.openweathermap.org";
    String path = "geo/1.0/direct";

    Dotenv dotenv = Dotenv.configure()
            .directory("./.env")
            .load();
    String APIKEY = dotenv.get("API_KEY");

    /**
     * Constructs the URL for the API request,
     * creates the HTTP request, executes the API call,
     * processes the response, and extracts the coordinates.
     * @param city The name of the city for which the coordinates are requested
     * @return GeoCoordinate The geographical coordinates corresponding to the specified city
     */
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
            System.out.println("DEBUG"+response);
            ResponseBody responseBody = response.body();
            if(responseBody==null) {
                throw new RuntimeException("RespondeBody is null");
            }
            JsonNode bodyNode = mapper.readTree(responseBody.string());

            System.out.println("DEBUG"+bodyNode.toString());
            GeoCoordinate coordinates = new GeoCoordinate();


            if (bodyNode.isArray() && !bodyNode.isEmpty()) {
                JsonNode firstItem = bodyNode.get(0);
                JsonNode latNode = firstItem.get("lat");
                JsonNode lonNode = firstItem.get("lon");
                if (latNode != null && lonNode != null) {
                    coordinates.setLatitude(latNode.asDouble());
                    coordinates.setLongitude(lonNode.asDouble());
                    coordinates.setCity(firstItem.get("name").asText());

                } else {
                    coordinates.setLatitude(0.0);
                    coordinates.setLongitude(0.0);
                    coordinates.setCity("");
                }
            } else {
                coordinates.setLatitude(0.0);
                coordinates.setLongitude(0.0);
                coordinates.setCity("");
            }

            return coordinates;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
