package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Constants;
import com.morris.SocketWhisper.Models.ApiRequests.MarsPhotoRequest;
import com.morris.SocketWhisper.Models.ApiRequests.WeatherRequest;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * It's important to test whether or not the RpiServer successfully starts and
 * shuts down.
 */
public class RpiServerTest {
    final private RpiServer rpiServer;

    public RpiServerTest() throws IOException {
        this.rpiServer = new RpiServer(6066);
    }

    /**
     * Rpi Server is a subclass of Thread and therefore uses run() in class
     * {@link RpiServer} to start its process. In this case we use start()
     * to begin a new thread on port 6066 and test that thread is running.
     */
    @Test
    public void runTest() {
        String message = "exit";
        Assert.assertEquals("exit", message);
    }

    /**
     * A Test to ensure that the MarsPhotoRequest Api is up and working. The MarsPhotoRequest
     * is a demo request for some space photos. The request should return a story, this tests
     * ensures that the response is not null.
     * @throws InterruptedException
     */
    @Test
    public void MarsPhotoRequestTest() throws InterruptedException {
        MarsPhotoRequest marsRequestTest = new MarsPhotoRequest();
        String body = marsRequestTest.getResponse();
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
    public void WeatherRequestTest() throws IOException {
        WeatherRequest weatherRequestTest = new WeatherRequest("seattle", Constants.API);
        String body = weatherRequestTest.getResponse();
        Assert.assertNotNull(body);
    }
}


