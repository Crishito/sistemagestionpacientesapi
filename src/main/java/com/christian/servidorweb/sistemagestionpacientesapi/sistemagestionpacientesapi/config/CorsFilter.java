package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;


@Provider // clase debe ser registrada y ejecutada como un proveedor de servicios.
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {

        // Permitir acceso desde cualquier origen
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");

        // permitir ciertos encabezados en las peticiones
        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");

        // permitir el uso de credenciales
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        // definir quee metodos http estn permitidos para las peticiones
        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");


        // Cuando el navegador hace una petición OPTIONS antes de la real
        // solo necesitamos devolver los encabezados CORS, no procesar la lógica de negocio
        if (requestContext.getMethod().equals("OPTIONS")) {
            responseContext.setStatus(200);
        }
    }
}