package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.config;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;


@ApplicationPath("/api") // Define el Application Path como /api
public class JakartaRestConfiguration extends Application {
    // JAX-RS escaneará automáticamente esta aplicación para encontrar y registrar
    // todos los recursos (@Path) y proveedores (@Provider, como el CorsFilter).
}