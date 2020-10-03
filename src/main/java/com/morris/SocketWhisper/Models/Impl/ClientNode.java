package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Models.Client;

import java.io.*;
import java.net.Socket;
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
        String serverName = "10.0.0.18";
        int serverPort = 6066;
        this.client = new Socket(serverName, serverPort);
    }

    @Override
    public void run() {
        Scanner clientInput = connectClientComm();
        try {
            DataOutputStream clientOut = buildClientToServerComm(this.client);
            DataInputStream serverIn = buildServerToClientComm(this.client);
            String whisper = showClientPrompt(clientInput);
            while (!whisper.isEmpty()) {
                if (isExitRequest(whisper)) {
                    shutDownClientConnection(this.client);
                }
                sendClientCommToServer(clientOut, whisper);
                if (!serverIn.readUTF().isEmpty()) {
                    showServerResponse(serverIn);
                }
                whisper = showClientPrompt(clientInput);
            }
        } catch (IOException e) {
            System.out.println("Server Interrupted!");
            try {
                shutDownClientConnection(this.client);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
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
        System.out.print("whisper: ");
        return clientInput.nextLine();
    }

    /**
     * Determines if client sends message to request disconnection from the Raspberry Pi Server.
     * @param whisper : client message.
     * @return boolean
     */
    private boolean isExitRequest(String whisper) {
        return whisper.equals("exit");
    }

    /**
     * Shuts down client communication to Raspberry PiServer by disconnecting {@link Socket}.
     * @param client the client to disconnect from Rpi.
     * @throws IOException some error occurs.
     */
    @Override
    public void shutDownClientConnection(Socket client) throws IOException {
        client.close();
        System.out.println("Client shutting down...");
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
        System.out.println(serverMessage.read());
    }
}