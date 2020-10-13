package com.morris.SocketWhisper;

import com.morris.SocketWhisper.Models.Impl.ClientNode;
import com.morris.SocketWhisper.Models.Impl.RpiServer;

import java.io.EOFException;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws IOException {

        final Logger ERROR_LOGGER = Logger.getLogger("errors");

        /* Creates Scanner object for user input */
        Scanner input = new Scanner(System.in);

        /* allows user to choose between server or client node */
        String nodeType = chooseNodeType(input);

        /* user choose server, attempt to start Rpi Server */
        if ( nodeType.equals("s") || nodeType.equals("s".toUpperCase()) ) {
            try {
                startRpiServer(input);
            } catch (EOFException e) {
                ERROR_LOGGER.log(Level.SEVERE, "error occurred closing client");
                System.out.println("Error occurred closing client, restarting server...");
                startRpiServer(input);
            }

        } else {
            startClient();
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
     * @throws IOException some error occurred
     */
    public static void startRpiServer(Scanner input) throws IOException {
        System.out.print("Please enter port number: ");
        int port = input.nextInt();
        Runnable rpiServer = new RpiServer(port);
        rpiServer.run();
    }

    /**
     * User has requested to run program as a service connecting to Raspberry Pi Server.
     * A {@link ExecutorService} is instantiated to handle this new client on its very
     * own thread. Allowing the Raspberry Pi to have multiple clients running on single
     * port.
     * @throws IOException
     */
    public static void startClient() throws IOException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ClientNode client = new ClientNode();
        executor.execute(client);
    }
}