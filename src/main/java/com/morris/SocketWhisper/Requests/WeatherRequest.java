package com.morris.SocketWhisper.Requests;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class WeatherRequest {
    final private Response response;

    public WeatherRequest(String city) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://community-open-weather-map.p.rapidapi.com/find?type=" +
                        "link%252C%20accurate&units=imperial%252C%20metric&q=" + city)
                .get()
                .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "e795adca4amsh2246a4f000734f5p1417c2jsn0f5a7833c8bb")
                .build();

        response = client.newCall(request).execute();
    }

    public String getResponse() throws IOException {
        return this.response.body().string();
    }
}





