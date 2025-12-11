package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.repositories;

import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.models.Paciente;
import java.util.List;
import java.util.Optional;

public interface PacienteRepository {

    List<Paciente> listar();

    Optional<Paciente> porId(Integer id);

    Optional<Paciente> porCedula(String cedula);

    Optional<Paciente> porCorreo(String correo);

    Paciente guardar(Paciente paciente);

    void eliminar(Integer id);

    boolean actualizarEstado(Integer id, Boolean estado);
}