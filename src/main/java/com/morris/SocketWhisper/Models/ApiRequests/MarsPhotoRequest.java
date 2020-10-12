package com.morris.SocketWhisper.Models.ApiRequests;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * MarsPhotoRequest currently implements a simple NASA Demo API which allows users to view a
 * few space photos and read information describing the photo. The intention for this class,
 * in the future, is to allow a certain number of NASA photo options to chose from and give
 * users the ability to view the information and photos. Please refer abstract parent class
 * {@link BasicRequest}.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public class MarsPhotoRequest extends BasicRequest {
    private final HttpClient client;
    private final HttpRequest request;

    public MarsPhotoRequest() {
        this.client = HttpClient.newHttpClient();

        this.request = HttpRequest.newBuilder(
                URI.create("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY"))
                .header("accept", "application/json")
                .build();
    }

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