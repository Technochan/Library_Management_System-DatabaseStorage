package com.zsgs.chandru.librarymanagement.databasejdbctypestorage.databaseconnectionobject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private Connection connection;
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String USERNAME = "root";
    private final String PASSWORD = "admin";

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
