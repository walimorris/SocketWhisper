package com.morris.SocketWhisper.RpiServer;

import com.morris.SocketWhisper.Requests.WeatherRequest;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RpiServer extends Thread {
    private final ServerSocket rpiServer;


    public RpiServer(int port) throws IOException {
        rpiServer = new ServerSocket(port);
    }

    public void run() {
        String localIP = rpiServer.getLocalSocketAddress().toString();
        String hostName = rpiServer.getInetAddress().getHostName();
        System.out.println("Waiting for client to connect on port: " + rpiServer.getLocalPort() + "...");
        System.out.println("Host Inet Address: " + localIP);
        System.out.println("Host Name: " + hostName);
        System.out.println("Socket Address: " + rpiServer.getLocalSocketAddress());

        try {
            Socket socket = rpiServer.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            String message = in.readUTF();

            while ( !(message).equals("exit") ) {
                System.out.println("Message Received: " + message);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("Whisper heard: " + message);
                message = in.readUTF();
                if ( message.equals("weather") ) {
                    out.writeUTF("[Whisper heard] which city: ");
                    message = in.readUTF(); // message should be a city at this point from client
                    /* at this point a request to the whether api should be made */
                    WeatherRequest weatherRequest = new WeatherRequest(message);
                    out.writeUTF(weatherRequest.getResponse());
                    message = in.readUTF(); // ensures rpiserver continues to wait for next message from client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


