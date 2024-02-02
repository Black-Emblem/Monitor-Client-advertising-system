package com.example.monitorclient.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconn {
    static Connection conn = null;

    public static void initializeConn() {
        if (conn == null){
            final String DB_URL = "jdbc:mariadb://192.168.1.8:3306/MMdb";

            final String USER = "admin";
            final String PASS = "admin";

            try {
                //Creating a connection to the database
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connection successful");
            } catch (SQLException e) {
                throw new RuntimeException("Connection with database failed", e);
            }
        }
    }

    public static Connection db_conn() throws SQLException {
        if (conn == null){
            final String DB_URL = "jdbc:mariadb://192.168.1.8:3306/MMdb";

            final String USER = "admin";
            final String PASS = "admin";

            try {
                //Creating a connection to the database
                conn = DriverManager.getConnection(DB_URL, USER, PASS);
                System.out.println("Connection successful");
            } catch (SQLException e) {
                throw new RuntimeException("Connection with database failed", e);
            }
            return  conn;
        } else {
            return conn;
        }
    }

    // Method to test connection with database
    public static String testConnection() {
        try {
            if(conn != null && !conn.isClosed()) {
                return "Connection with database is successful.";
            } else {
                return "No connection with database.";
            }
        } catch(SQLException e) {
            return "An error occurred while testing the database connection.";
        }
    }
}