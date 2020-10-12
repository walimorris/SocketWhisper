package com.morris.SocketWhisper.Models.ApiRequests;

import com.morris.SocketWhisper.Constants;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Weather request utilizes the Open Weather Map api to request detailed weather information
 * about a certain city through HTTP Request {@link HttpRequest}. {@link HttpResponse} and
 * {@link HttpClient}. It is best to give the full name me of the city, Weather Map Api returns
 * better results.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public class WeatherRequest extends BasicRequest {
    private final HttpClient client;
    private final HttpRequest request;

    /**
     * Http request to Open Weather Map Api for a certain city.
     * @param city which city to request detailed weather information about.
     * @throws IOException in case some error happens during request.
     */
    public WeatherRequest(String city) throws IOException {
        this.client = HttpClient.newHttpClient();
        this.request = HttpRequest.newBuilder(
                URI.create("https://community-open-weather-map.p.rapidapi.com/find?type=" +
                        "link%252C%20accurate&units=imperial%252C%20metric&q=" + city))
                .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .header("x-rapidapi-key", Constants.API)
                .build();
    }

    /**
     * Returns a response from the Weather Map Api in JSON format. There is tons of information to unpack
     * here, including fog levels, latitude, longitude and much more.
     * @return a String formatted by the buildResponse() methods from {@link BasicRequest} abstract class.
     * @throws IOException in case some errors building response.
     */
    @Override
    @NotNull
    public String getResponse() {
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            return "Error, unable to process request";
        }
        return buildResponse(response);
    }
}