package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Constants;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * These series of RaspberryPi, as server test, will conduct tests on multiple functions of the
 * RaspberryPi server.
 *
 * NOTE: Ensure RaspberryPi server is running.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
@DisplayName("RaspberryPi Server Test")
public class RpiServerTest {

    /**
     * One of the first functions of the RaspberryPi server is reporting Host information.
     * This test ensures that The RaspberryPi ServerSocket is initialized and connected,
     * the correct port is used and all Server information is reported.
     *
     * @author Wali Morris<walimmorris@gmail.com>
     * @throws IOException : some error occurs during ServerSocket initialization.
     */
    @Test
    @DisplayName("Showing Server(Host) Info Test")
    public void showServerInfoTest() throws IOException {
        ServerSocket rpiServer = new ServerSocket(Constants.PORT);
        String localIP = rpiServer.getLocalSocketAddress().toString();
        String hostName = rpiServer.getInetAddress().getHostName();
        String localSocketAddress = rpiServer.getLocalSocketAddress().toString();
        System.out.println("Test: Local IP Address = " +  localIP);
        System.out.println("Test: Local Host Name = " + hostName);
        System.out.println("Test: Local Socket Address = " + localSocketAddress);
        Assert.assertNotNull(localIP);
        Assert.assertNotNull(hostName);
        Assert.assertNotNull(rpiServer.getLocalSocketAddress());
        Assert.assertEquals(rpiServer.getLocalPort(), 6066);
    }

    @Test
    @DisplayName("Testing to Validate Raspberry Pi status(closed or open)")
    public void IsDownTest() throws IOException {
        ServerSocket rpiServer = new ServerSocket(Constants.PORT);
        Assert.assertFalse(rpiServer.isClosed());
        rpiServer.close();
        Assert.assertTrue(rpiServer.isClosed());
    }
}