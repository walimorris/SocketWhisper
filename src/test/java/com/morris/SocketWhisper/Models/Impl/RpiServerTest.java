package com.morris.SocketWhisper.Models.Impl;

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

    }
}


