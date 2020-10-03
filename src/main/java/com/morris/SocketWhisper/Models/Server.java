package com.morris.SocketWhisper.Models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server interface contains the basic standards of communication
 * between a Server-Client relationship see {@link Client}.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public interface Server extends Runnable {

    void showServerInfo(ServerSocket rpiServer);

    Socket listen(ServerSocket rpiServer) throws IOException;

    String getClientRequest(DataInputStream in) throws IOException;

    void showClientMessage(String message);

    void sendClientWhisperEcho(String message, DataOutputStream out) throws IOException;

    void disconnectClient(Socket clientSocket, DataOutputStream out, DataInputStream in) throws IOException;
}