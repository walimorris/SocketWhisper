package com.morris.SocketWhisper.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JokesDB {
    public static void connect() {
        Connection conn = null;
        try {

            String url = "jdbc:sqlite/home/pi/jokes.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to Jokes Database has been established");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
