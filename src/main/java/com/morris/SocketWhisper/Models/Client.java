package com.morris.SocketWhisper.Models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * An Interface which describes a Client in Server-Client relationship between Raspberry Pi as
 * Server and PC as client. A client is any node who should request communication with Rpi. This
 * interface describes what communication can happen within this Server-Client relationship.
 *
 * @author Wali Morris<walimorris@gmail.com>
 *
 */
public interface Client extends Runnable {

    void run();

    Scanner connectClientComm();

    DataOutputStream buildClientToServerComm(Socket client) throws IOException;

    String showClientPrompt(Scanner clientInput);

    void shutDownClientConnection();

    void sendClientCommToServer(DataOutputStream clientOut, String whisper) throws IOException;

    DataInputStream buildServerToClientComm(Socket client) throws IOException;

    void showServerResponse(DataInputStream serverMessage) throws IOException;
}