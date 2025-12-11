package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services.exceptions;

public class CorreoYaExisteException extends RuntimeException {

    public CorreoYaExisteException(String message) {
        super(message);
    }
}