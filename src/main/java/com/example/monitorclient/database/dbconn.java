package com.example.monitorclient.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class dbconn {
    static Connection conn = null;

    public static void initializeConn() {
        if (conn == null){String DB_URL = "";
            String USER = "";
            String PASS = "";

            // Read data from CSV file
            try {
                Scanner scanner = new Scanner(new File("data.csv"));
                while (scanner.hasNext()) {
                    String[] data = scanner.next().split(",");
                    String ip = data[2];
                    String port = data[3];
                    String dbName = data[1];
                    USER = data[4];
                    PASS = data[5];

                    DB_URL = "jdbc:mariadb://" + ip + ":" + port + "/" + dbName;
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException("File not found", e);
            }

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
            initializeConn();
            return conn;
        } else {
            try {
                if(conn != null && !conn.isClosed()) {
                    initializeConn();
                    return conn;
                } else {
                    return conn;
                }
            } catch(SQLException e){
                return conn;
            }
        }
    }

    // Method to test connection with database
    public static String testConnection() {
        conn=null;
        initializeConn();
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