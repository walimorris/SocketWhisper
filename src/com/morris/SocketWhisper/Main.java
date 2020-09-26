package com.morris.SocketWhisper;

import com.morris.SocketWhisper.RpiServer.RpiServer;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        /* Creates Scanner object for user input */
        Scanner input = new Scanner(System.in);

        /* allows user to choose between server or client node */
        String nodeType = chooseNodeType(input);

        /* user choose server, attempt to start Rpi Server */
        if ( nodeType.equals("s") || nodeType.equals("s".toUpperCase()) ) {
            startRpiServer(input);
        }
    }

    /**
     * Node type refers to server or client type. This functionality allows
     * the user to choose which node type to run. In the case of SocketWhisper
     * the server should run on the raspberry pi, so in almost all cases when
     * running this program from the Rpi, user should choose "s" as node type.
     * When running as client, user should choose "c".
     * @param input : which node type to run
     * @return String
     */
    public static String chooseNodeType(Scanner input) {
        System.out.println("Welcome to SocketWhisper!");
        System.out.print("This node is s[server] c[client] : ");
        return input.next();
    }

    /**
     * Server node type has been chosen by user, this functionality starts the
     * server building process.
     * @param input : user input
     * @throws IOException
     */
    public static void startRpiServer(Scanner input) throws IOException {
        System.out.print("Please enter port number: ");
        int port = input.nextInt();
        Thread thread = new RpiServer(port);
        thread.start();
    }
}




