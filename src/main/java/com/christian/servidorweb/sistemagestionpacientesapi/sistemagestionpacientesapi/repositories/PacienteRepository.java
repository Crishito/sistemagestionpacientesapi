package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.repositories;


import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.models.Paciente;
import java.util.List;
import java.util.Optional; // Se añade Optional para un manejo robusto de la búsqueda

public interface PacienteRepository {

    List<Paciente> listar();

    Optional<Paciente> porId(Integer id);

    Paciente guardar(Paciente paciente);

    void eliminar(Integer id);

    boolean actualizarEstado(Integer id, Boolean activo);
}