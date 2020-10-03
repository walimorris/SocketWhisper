package com.morris.SocketWhisper.Models.Impl;

import com.morris.SocketWhisper.Models.Client;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


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

            while ( true ) {
                String whisper = showClientPrompt(clientInput);
                if (isExitRequest(whisper)) {
                    shutDownClientConnection(this.client);
                }
                sendClientCommToServer(clientOut, whisper);
                try (DataInputStream serverIn = buildServerToClientComm(this.client)) {
                    showSeverResponse(serverIn);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
     * @param client node talking to RpiServer.
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
    public String showClientPrompt(Scanner clientInput) {
        System.out.print("whisper: ");
        return clientInput.nextLine();
    }

    private boolean isExitRequest(String whisper) {
        return whisper.equals("exit");
    }

    public void shutDownClientConnection(Socket client) throws IOException {
        client.close();
        System.out.println("Client shutting down...");
        System.exit(0);
    }

    public void sendClientCommToServer(DataOutputStream clientOut, String whisper) throws IOException {
        clientOut.writeUTF(whisper);
    }

    public DataInputStream buildServerToClientComm(Socket client) throws IOException {
        DataInputStream serverToClient = (DataInputStream) client.getInputStream();
        return new DataInputStream(serverToClient);
    }

    public void showSeverResponse(DataInputStream serverMessage) throws IOException {
        System.out.println(serverMessage.readUTF());
    }
}
