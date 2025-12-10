package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;


@Provider // Indica a JAX-RS que esta clase debe ser registrada y ejecutada como un proveedor de servicios.
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        // 1. Permitir acceso desde cualquier origen (el '*' lo permite todo)
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // 2. Permitir ciertos encabezados en las peticiones
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");

        // 3. Permitir el uso de credenciales (si fuera necesario, aunque el '*' de arriba lo permite)
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // 4. Definir qué métodos HTTP están permitidos para las peticiones CORS (ej: GET, POST, PUT)
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // ** Manejo de la Petición OPTIONS (Preflight) **
        // Cuando el navegador hace una petición OPTIONS antes de la real (preflight),
        // solo necesitamos devolver los encabezados CORS, no procesar la lógica de negocio.
        if (requestContext.getMethod().equals("OPTIONS")) {
            responseContext.setStatus(200);
        }
    }
}