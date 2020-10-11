package com.morris.SocketWhisper.Models.ApiRequests;

import com.morris.SocketWhisper.Constants;
import com.morris.SocketWhisper.Models.BasicRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Weather request utilizes the Open Weather Map api to request detailed weather information
 * about a certain city through HTTP Request {@link OkHttpClient}. It is best to give the full
 * name of the city, Weather Map Api returns better results.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public class WeatherRequest implements BasicRequest {
    final private Response response;

    /**
     * Http request to Open Weather Map Api for a certain city.
     * @param city which city to request detailed weather information about.
     * @throws IOException in case some error happens during request.
     */
    public WeatherRequest(String city) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/find?type=" +
                        "link%252C%20accurate&units=imperial%252C%20metric&q=" + city)
                .get()
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .addHeader("x-rapidapi-key", Constants.API)
                .build();

        response = client.newCall(request).execute();
    }

    /**
     * Returns a response from the Weather Map Api in JSON format. There is tons of information to unpack
     * here, including fog levels, latitude, longitude and much more.
     * @return a String in Json format {@link com.squareup.okhttp.Request}.
     * @throws IOException in case some errors building response.
     */
    @Override
    public String getResponse() throws IOException {
        return this.response.body().string();
    }
}