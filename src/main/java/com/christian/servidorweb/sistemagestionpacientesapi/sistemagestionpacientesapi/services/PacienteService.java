package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services;


import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.models.Paciente;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.repositories.PacienteRepository; // 🛑 CORREGIDO
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteRepository repository;

    /**
     * Requisito: GET /api/pacientes
     */
    public List<Paciente> listar() {
        return repository.listar();
    }

    /**
     * Requisito: GET /api/pacientes/{id}
     */
    public Optional<Paciente> porId(Integer id) {
        // Asumimos que el repositorio ya devuelve Optional<Paciente>
        return repository.porId(id);
    }


    public Paciente guardar(Paciente paciente) {
        return repository.guardar(paciente);
    }

    public void eliminar(Integer id) {
        repository.eliminar(id);
    }


    public boolean actualizarEstado(Integer id, Boolean estado) {
        return repository.actualizarEstado(id, estado);
    }
}