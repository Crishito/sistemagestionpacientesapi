package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.models;


import java.io.Serializable;

public class Paciente implements Serializable {

    // Atributos privados
    private Integer id;
    private String cedula;
    private String nombre;
    private String correo;
    private Integer edad;
    private String direccion;
    private Boolean activo;

    // Constructor vacío (Esencial para JAX-RS/JSON)
    public Paciente() {
    }

    // Constructor completo
    public Paciente(Integer id, String cedula, String nombre, String correo, Integer edad, String direccion, Boolean activo) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.direccion = direccion;
        this.activo = activo;
    }

    // --- Getters y Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Boolean isActivo() { // Convención para booleanos
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}