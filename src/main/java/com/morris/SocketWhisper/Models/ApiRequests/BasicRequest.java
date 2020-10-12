package com.morris.SocketWhisper.Models.ApiRequests;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * The abstract BasicRequest class will act as the parent of many of the API requests classes
 * that will inherit some of the implemented methods. Many of the abstract methods will be
 * implemented within child classes, that may need such a method but does not yet know how
 * it should use such an abstract method.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public abstract class BasicRequest {

    /**
     * Used to return the {@link HttpResponse} from API requests.
     * @return String HttpResponse
     * @throws IOException some error occurs
     * @throws InterruptedException some error occurs
     */
    public abstract String getResponse() throws IOException, InterruptedException;

    /**
     * Builds a formatted Response from an API HttpResponse. This method ensures that
     * an HttpResponse can be read within the available viewing screen.
     * @param response A API {@link HttpResponse}.
     * @return String formatted response.
     */
    public String buildResponse(HttpResponse<String> response) {
        StringBuilder bodyResponse = new StringBuilder();
        if ( response != null ) {
            String body = response.body();
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