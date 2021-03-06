package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Models.ApiRequests.MarsPhotoRequest;
import com.morris.SocketWhisper.Models.ApiRequests.WeatherRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

/**
 * It's important to test the working elements of the RpiServer and ClientNodes that connect to it.
 * Ensuring that processes are started, ended, properly built and exited are critical to ensuring
 * the function of the RpiServer and ClientNodes. Here contains a series of tests to ensure that
 * API requests are properly handled and executed.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
@DisplayName("Api Requests Test Cases")
public class ApiRequestsTest {

    /**
     * A Test to ensure that the MarsPhotoRequest Api is up and working. The MarsPhotoRequest
     * is a demo request for some space photos. The request should return a story, this tests
     * ensures that the response is not null.
     * @throws InterruptedException
     */
    @Test
    @DisplayName("MarsPhotoRequest Test")
    public void MarsPhotoRequestTest() {
        MarsPhotoRequest marsRequestTest = new MarsPhotoRequest();
        String body = marsRequestTest.getResponse();

        /* print body in response, for now, to view formatted response. */
        System.out.println(body);
        Assert.assertNotNull(body);
    }

    /**
     * The WeatherRequest takes a String city and API key and returns a String of weather info.
     * The returned response should not be null. A not null response tests weather the API is
     * working properly. At a minimum, api should response with message of incorrect api key or
     * request for the user.
     * @throws IOException
     */
    @Test
    @DisplayName("WeatherRequest Test")
    public void WeatherRequestTest() throws IOException {
        WeatherRequest weatherRequestTest = new WeatherRequest("seattle");
        String body = weatherRequestTest.getResponse();

        /* Print body of response, for now, to view formatted body of response. */
        System.out.println(body);
        Assert.assertNotNull(body);

        /* If api key is incorrect, weather api sends a short message. This test ensures that although
         * response is not null, also that it is not just a short error message but rather the weather
         * information.
         */
        Assert.assertTrue(body.length() > 150);
    }
}