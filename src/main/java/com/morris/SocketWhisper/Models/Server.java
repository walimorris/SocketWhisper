package com.morris.SocketWhisper.Models;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Server interface contains the basic standards of communication
 * between a Server-Client relationship see {@link Client}.
 *
 * @author Wali Morris<walimmorris@gmail.com>
 */
public interface Server {

    void showServerInfo(ServerSocket rpiServer);

    Socket listen(ServerSocket rpiServer) throws IOException;

    DataInputStream getClientRequest(Socket clientSocket) throws IOException;

    void showClientMessage(String message);

    void sendClientWhisperEcho(Socket clientSocket, String message) throws IOException;

    void disconnectClient(Socket clientSocket) throws IOException;
}