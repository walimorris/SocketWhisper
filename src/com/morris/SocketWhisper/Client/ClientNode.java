package com.morris.SocketWhisper.Client;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;


public class ClientNode {
    private final Socket client;

    public ClientNode() throws IOException {
        String serverName = "tuvok2";
        int serverPort = 6066;
        client = new Socket(serverName, serverPort);
    }

    public void run() {
        System.out.println("connected to server: " + client.getInetAddress().getHostAddress());
        System.out.println("connected to port: " + client.getLocalPort());
        Scanner userInput = new Scanner(System.in);

        try {
            System.out.println("Connected to: " + client.getRemoteSocketAddress());
            OutputStream clientToServer = client.getOutputStream();
            DataOutputStream clientOut = new DataOutputStream(clientToServer);

            while (true) {
                System.out.print("whisper: ");
                String whisper = userInput.next();

                if (whisper.equals("exit")) {
                    System.exit(0);
                }

                clientOut.writeUTF(whisper);
                InputStream serverToClient = client.getInputStream();
                DataInputStream serverIn = new DataInputStream(serverToClient);
                System.out.println("Server says: " + serverIn.readUTF());
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






