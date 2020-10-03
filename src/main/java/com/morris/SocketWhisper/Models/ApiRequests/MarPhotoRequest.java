package com.morris.SocketWhisper.Models.ApiRequests;

import com.morris.SocketWhisper.Models.BasicRequest;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MarPhotoRequest implements BasicRequest {
    private final Response response;
    private String earthDate;

    public MarPhotoRequest(String earthDate) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String apiKey = "DEMO_KEY";
        Request request = new Request.Builder()
                .url("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=" +
                        earthDate + "&api_key=" + apiKey)
                .build();
        response = client.newCall(request).execute();
    }

    @Override
    public String getResponse() throws IOException {
        return this.response.body().toString();
    }
}
