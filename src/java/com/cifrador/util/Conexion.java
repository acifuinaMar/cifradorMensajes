package com.cifrador.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    private static final String URL = "jdbc:postgresql://localhost:5432/cifrador";
    private static final String USER = "postgres";
    private static final String PASSWORD = "maryori123";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Todo nice con la conexión!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
