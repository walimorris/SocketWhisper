package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Models.ApiRequests.MarsPhotoRequest;
import com.morris.SocketWhisper.Models.Server;
import com.morris.SocketWhisper.Models.ApiRequests.WeatherRequest;
import com.morris.SocketWhisper.db.JokesDB;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The RpiServer Class extends Thread as every client will run on its very own thread. The Rpi
 * can conduct different actions, gaining basic server-client communication abilities from the
 * {@link Server} interface. Other abilities{@link WeatherRequest} and {@link MarsPhotoRequest}
 * come directly from the RpiServer class. The Rpi begins in a "listen" state, waiting for
 * client requests coming from a {@link ClientNode}. Once the RpiServer is satisfied,
 * communications can begin.
 *
 * @author Wali Morris<walimorris@gmail.com>
 */
public class RpiServer implements Server {
    private final static Logger AUDIT_LOGGER = Logger.getLogger("requests");
    private final static Logger ERROR_LOGGER = Logger.getLogger("errors");
    private final ServerSocket rpiServer;

    public RpiServer(int port) throws IOException {
        this.rpiServer = new ServerSocket(port);
    }

    @Override
    public void run() {
        showServerInfo(this.rpiServer);

        try {

            while (true) {

                Date date = new Date();
                Socket clientSocket = listen(this.rpiServer);
                String clientWhisper = getClientRequest(clientSocket).toString();
                AUDIT_LOGGER.info(date + " " + "request from: " + clientSocket.getInetAddress() +
                        " request = " + clientWhisper);

                switch (clientWhisper) {
                    case "weather" :
                        fetchWeatherRequest(clientSocket);
                        AUDIT_LOGGER.info(date + " " + "request from: " + clientSocket.getInetAddress() +
                                " request=WeatherRequest");
                        break;

                    case "mars" :
                        fetchMarsPhotoRequest(clientSocket);
                        AUDIT_LOGGER.info(date + " " + "request from: " + clientSocket.getInetAddress() +
                                " request=MarsPhotoRequest");
                        break;

                    case "jokes" :
                        JokesDB.connect();
                        String conn = "connected to JokesDB";
                        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                        out.writeUTF(conn);
                        break;

                    case "exit":
                        disconnectClient(clientSocket);
                        AUDIT_LOGGER.info(date + " " + "request from: " + clientSocket.getInetAddress() +
                                "status: " + "disconnect=" + clientSocket.isClosed());
                        break;

                    case "default" :
                        disconnectClient(clientSocket); // implement receiving a int thats not an option
                        break;
                }
                showClientMessage(clientWhisper);
                sendClientWhisperEcho(clientSocket, clientWhisper);
            }
        } catch (IOException | InterruptedException e) {
            ERROR_LOGGER.log(Level.SEVERE, "couldn't receive client request", e.getMessage());
        }
        this.run();
    }

    /**
     * Shows current Raspberry Server communication info.
     *
     * @param rpiServer {@link ServerSocket} communication port.
     */
    @Override
    public void showServerInfo(ServerSocket rpiServer) {
        String localIP = rpiServer.getLocalSocketAddress().toString();
        String hostName = rpiServer.getInetAddress().getHostName();
        System.out.println("Waiting for client to connect on port: " + rpiServer.getLocalPort() + "...");
        System.out.println("Host Inet Address: " + localIP);
        System.out.println("Host Name: " + hostName);
        System.out.println("Socket Address: " + rpiServer.getLocalSocketAddress());
    }

    /**
     * Raspberry Pi server should listen for any client who requests to connect over the local network. Once Rpi
     * server is satisfied, the Rpi {@link ServerSocket} will bind to client {@link Socket} and receive client's
     * message. Communication has officially begun once Rpi Server has accepted and binds sockets for communication.
     *
     * @param rpiServer A Raspberry Pi Server Socket {@link ServerSocket}.
     * @return A bound client socket {@link Socket}.
     * @throws IOException some error occurs
     */
    @Override
    public Socket listen(ServerSocket rpiServer) throws IOException {
        return rpiServer.accept();
    }

    public DataInputStream getClientRequest(Socket clientSocket) throws IOException {
        return new DataInputStream(clientSocket.getInputStream());
    }

    /**
     * Outputs message from client on RpiServer stdout side.
     *
     * @param message message received from client.
     */
    @Override
    public void showClientMessage(String message) {
        System.out.println("Message Received: " + message);
    }

    /**
     * Sends a message to client through clients binded {@link Socket}. This is an echo message; a message from
     * the client that the server received and is repeated it back to client.
     *
     * @param clientSocket binded client {@link Socket} which is allowing Server-Client communication.
     * @param message      original message from client which server is sending back as echo.
     * @throws IOException some error occurs.
     */
    public void sendClientWhisperEcho(Socket clientSocket, String message) throws IOException {
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeUTF("Whisper heard: " + message);
    }

    /**
     * Client requests a weather report for a certain city. Server fetches and executes a new
     * WeatherRequest see {@link WeatherRequest}.
     *
     * @param clientSocket client {@link Socket}
     * @throws IOException some error occurs.
     */
    public void fetchWeatherRequest(Socket clientSocket) throws IOException {
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        out.writeUTF("[Whisper heard] which city: ");
        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        String city = in.readUTF();
        DataOutputStream out2 = new DataOutputStream(clientSocket.getOutputStream());
        out2.writeUTF("[Whisper heard] enter api key: ");
        DataInputStream in2 = new DataInputStream(clientSocket.getInputStream());
        String apiKey = in2.readUTF();
        WeatherRequest weatherRequest = new WeatherRequest(city, apiKey);
        out2.writeUTF(weatherRequest.getResponse());
    }

    public void fetchMarsPhotoRequest(Socket clientSocket) throws IOException, InterruptedException {
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        MarsPhotoRequest marsPhotoRequest = new MarsPhotoRequest();
        out.writeUTF(marsPhotoRequest.getResponse());
    }

    public void disconnectClient(Socket clientSocket) throws IOException {
        System.out.println("client at : " + clientSocket.getInetAddress() + "requesting disconnect");
        clientSocket.close();
        System.out.println(clientSocket.getInetAddress() + "disconnected!");
        this.run();
    }
}