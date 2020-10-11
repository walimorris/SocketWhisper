package com.morris.SocketWhisper.Models.ApiRequests;

import com.morris.SocketWhisper.Models.BasicRequest;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MarsPhotoRequest implements BasicRequest {
    HttpClient client;
    HttpRequest request;
    HttpResponse<String> response;

    public MarsPhotoRequest() {
        this.client = HttpClient.newHttpClient();

        this.request = HttpRequest.newBuilder(
                URI.create("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"))
                .header("accept", "application/json")
                .build();
    }

    @Override @NotNull
    public String getResponse() throws InterruptedException {
        try {
            this.response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            return "Error, unable to process request";
        }
        return buildResponse();
    }

    @NotNull
    private String buildResponse() {
        StringBuilder bodyResponse = new StringBuilder();
        if ( this.response != null ) {
            String body = this.response.body();
            int i = 0, j = 0;
            while (i < body.length()) {
                bodyResponse.append(body.charAt(i));
                if (i == j + 100) {
                    bodyResponse.append("\n");
                    j = i;
                }
                i++;
            }
        }
        return bodyResponse.toString();
    }
}