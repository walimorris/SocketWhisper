package com.morris.SocketWhisper.RpiServer;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class RpiServer extends Thread {
    private final ServerSocket rpiServer;


    public RpiServer(int port) throws IOException {
        rpiServer = new ServerSocket(port);
        rpiServer.setSoTimeout(10000);
    }

    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for client to connect on port: " + rpiServer.getLocalPort() + "...");
                Socket socket = rpiServer.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                String message = in.readUTF();
                System.out.println("Message Received: " + message);
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF("Echo: " + message);
                if (message.equalsIgnoreCase("exit")) {
                    break;
                }
                System.out.println("Shutting down Raspberry Pi Server");
                System.out.println("Goodbye!");
                close(rpiServer, in, out, socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(ServerSocket rpiServer, DataInputStream in,
                      DataOutputStream out, Socket socket) throws IOException {
        in.close();
        out.close();
        socket.close();
        rpiServer.close();
    }
}


