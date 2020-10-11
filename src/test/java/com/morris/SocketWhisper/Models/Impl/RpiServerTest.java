package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Constants;
import com.morris.SocketWhisper.Models.ApiRequests.MarsPhotoRequest;
import com.morris.SocketWhisper.Models.ApiRequests.WeatherRequest;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * It's important to test the working elements of the RpiServer and ClientNodes that connect to it.
 * Ensuring that processes are started, ended, properly built and exited are critical to ensuring
 * the function of the RpiServer and ClientNodes. Here contains a series of tests to ensure that
 * API requests, executors, and critical functions work properly.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public class RpiServerTest {

    @Test
    public void RpiServerExecutionTest() throws IOException {
        RpiServer rpiServer = new RpiServer(6066);
        rpiServer.run();

        /* A RpiServer has been instantiated and run, RpiServer class has a method called
         * isDown() which returns true if RpiServer is closed and false otherwise. This
         * test utilizes this method to ensure the raspberrypi is up and running.
         */
        Assert.assertFalse(rpiServer.isDown());
    }

    /**
     * Each client is run on its own thread using the {@link ExecutorService}.
     * This tests ensures that the thread is powered by the executor, then it's
     * shutdown and tested to ensure that the thread is powered down.
     * @throws IOException
     */
    @Test
    public void clientNodeExecutorThreadTest() throws IOException {
        ExecutorService ex = Executors.newSingleThreadExecutor();
        ClientNode client = new ClientNode();
        ex.execute(client);
        Assert.assertFalse(ex.isShutdown());
        ex.shutdown();
        Assert.assertTrue(ex.isShutdown());
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