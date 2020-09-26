package com.morris.SocketWhisper;

import com.morris.SocketWhisper.RpiServer.RpiServer;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter port number: ");
        int port = input.nextInt();
        Thread thread = new RpiServer(port);
        thread.start();
    }
}




