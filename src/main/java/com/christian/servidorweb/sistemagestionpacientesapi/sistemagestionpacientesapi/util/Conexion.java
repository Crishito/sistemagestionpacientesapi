package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.util;

//package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.util.Conexion.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // URL usando el nombre del servicio de Docker Compose: mysqldb
    private static final String URL = "jdbc:mysql://localhost:3306/hospitaldb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    // Credenciales del contenedor Docker Compose
    private static final String USER = "root";
    private static final String PASS = "root";


    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL.", e);
        }
    }
}