package com.morris.SocketWhisper.Models;

import java.io.IOException;

/**
 * Request interface holds some basic request functions for any class that implements it.
 * For examples @see {@link com.morris.SocketWhisper.Models.ApiRequests.WeatherRequest}
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public interface Request {

    String getResponse() throws IOException;
}