package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services.exceptions;

public class CedulaYaExisteException extends RuntimeException {

    public CedulaYaExisteException(String message) {
        super(message);
    }
}