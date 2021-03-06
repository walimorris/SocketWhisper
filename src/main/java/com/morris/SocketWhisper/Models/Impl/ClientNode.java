package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Constants;
import com.morris.SocketWhisper.Models.Client;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The implementation of a ClientNode who will request communication to Raspberry Pi. This ClientNode
 * class implements the Client interface and describes how Raspberry Pi and a client will communicate.
 *
 * @author Wali Morris<walimorris@gmail.com>
 */
public class ClientNode implements Client {
    private final Socket client;

    public ClientNode() throws IOException {
        String serverName = Constants.HOST;
        int serverPort = Constants.PORT;
        this.client = new Socket(serverName, serverPort);
    }

    @Override
    public void run() {
        Scanner clientInput = connectClientComm();
        try {
            DataOutputStream clientOut = buildClientToServerComm(this.client);
            DataInputStream serverIn = buildServerToClientComm(this.client);
            Map<String, String> options = getPromptMap();

            while ( true ) {
                String whisper = showClientPrompt(clientInput);

                switch( whisper ) {
                    case "4":
                    shutDownClientConnection();
                    break;
                }
                String whisperStr = null;
                for ( String key : options.keySet() ) {
                    if (key.equals(whisper)) {
                        whisperStr = options.get(key);
                    }
                }
                if ( whisperStr == null ) {
                    whisperStr = "chose a unavailable option!";
                }
                sendClientCommToServer(clientOut, whisperStr);
                showServerResponse(serverIn);
            }
        } catch (IOException e) {
            System.out.println("Server Interrupted!");
        }
    }

    /**
     * Builds a report depicting host and client connection information and a client
     * communication {@link Scanner} object for client input.
     * @return client communication Scanner.
     */
    @Override
    public Scanner connectClientComm() {
        System.out.println("connected to server: " + client.getInetAddress().getHostAddress());
        System.out.println("connected to port: " + client.getLocalPort());
        System.out.println("Connected to: " + client.getRemoteSocketAddress());
        return new Scanner(System.in);
    }

    /**
     * Builds a communication {@link DataOutputStream} from client to server which sends
     * client output to server.
     * @param client connected to this socket communication {@link Socket}.
     * @return A new DataOutputStream consisting of client information.
     * @throws IOException in case error occurs while build DataOutputStream.
     */
    @Override
    public DataOutputStream buildClientToServerComm(Socket client) throws IOException {
        OutputStream clientToServer = client.getOutputStream();
        return new DataOutputStream(clientToServer);
    }

    /**
     * Prompts a client to type something to {@link Scanner} object, this acts as clientInput.
     * @param clientInput  A Scanner object.
     * @return String which is client input.
     */
    @Override
    public String showClientPrompt(Scanner clientInput) {
        Map<String, String> options = getPromptMap();
        StringBuilder prompt = new StringBuilder();
        options.forEach((key, value) -> prompt
                .append(key).append(": ")
                .append(value)
                .append("\n"));
        System.out.println(prompt.toString());
        System.out.print("whisper: ");
        return clientInput.nextLine();
    }

    /**
     * Helper method to build the client prompt from {@link HashMap}.
     * @return Map containing different user options.
     */
    public Map<String, String> getPromptMap() {
        Map<String, String> options = new HashMap<>();
        options.put("1", "weather");
        options.put("2", "mars");
        options.put("3", "jokes");
        options.put("4", "exit");
        return options;
    }

    /**
     * Shuts down client communication to Raspberry PiServer by shutting down program and
     * handling client socket closure on server side.
     */
    @Override
    public void shutDownClientConnection() {
        System.out.println("Client shutting down, thanks for using SocketWhisper. Goodbye!");
    }

    /**
     * Sends client communication to server through a whisper - a message from user input.
     * @param clientOut clients outgoing communication {@link DataOutputStream}.
     * @param whisper the actual message from client.
     * @throws IOException some error occurs.
     */
    @Override
    public void sendClientCommToServer(DataOutputStream clientOut, String whisper) throws IOException {
        clientOut.writeUTF(whisper);
    }

    /**
     * Builds a communication link through {@link Socket} that allows client to receive message from
     * Raspberry Pi server in form of a {@link DataInputStream} from the Rpi Server.
     * @param client client connected to this communication socket.
     * @return a new DataInputStream.
     * @throws IOException some error occurs
     */
    @Override
    public DataInputStream buildServerToClientComm(Socket client) throws IOException {
        return new DataInputStream(client.getInputStream());
    }

    /**
     * Shows the message from Raspberry Pi intended for client.
     * @param serverMessage a Message from server in form of {@link DataInputStream} to client.
     * @throws IOException some error occurs.
     */
    @Override
    public void showServerResponse(DataInputStream serverMessage) throws IOException {
        System.out.println(serverMessage.readUTF());
    }
}