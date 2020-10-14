package com.morris.SocketWhisper.db;

import java.sql.*;

public class JokesDB {
    private String jokes;

    public void connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite/home/pi/jokes.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to Jokes Database has been established");
            this.jokes = readAllFromLightJokes(conn);
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

    private String readAllFromLightJokes(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM lightjokes");
            return resultSet.toString();
        } catch (SQLException e) {
            return "Some error occurred reading sql-result";
        }
    }

    public String getJokes() {
        return this.jokes;
    }
}