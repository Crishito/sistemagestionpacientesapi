package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.util;

//package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.util.Conexion.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // URL usando el nombre del servicio de Docker Compose: mysqldb
    private static final String URL = "jdbc:mysql://mysqldb:3306/hospitaldb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    // Credenciales del contenedor Docker Compose
    private static final String USER = "userdb";
    private static final String PASS = "password";
    // NOTA: Si usas root para conectar, cambia USER a "root" y PASS a "root"

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL.", e);
        }
    }
}