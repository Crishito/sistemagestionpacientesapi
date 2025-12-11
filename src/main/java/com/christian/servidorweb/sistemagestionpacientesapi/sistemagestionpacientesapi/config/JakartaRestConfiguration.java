package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.config;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api") // define el applicacion path como /api
public class JakartaRestConfiguration extends Application {
    // escaneara automaticamente esta aplicacion para encontrar y registrar

}