package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services;

import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.models.Paciente;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.repositories.PacienteRepository;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services.exceptions.CedulaYaExisteException;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services.exceptions.CorreoYaExisteException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteRepository repository;

    public List<Paciente> listar() {
        return repository.listar();
    }

    public Optional<Paciente> porId(Integer id) {
        return repository.porId(id);
    }

    public Paciente guardar(Paciente paciente) {

        // 1. Validar para la creación (id == null)
        if (paciente.getId() == null) {

            if (repository.porCedula(paciente.getCedula()).isPresent()) {
                throw new CedulaYaExisteException("La cédula '" + paciente.getCedula() + "' ya está registrada.");
            }

            if (repository.porCorreo(paciente.getCorreo()).isPresent()) {
                throw new CorreoYaExisteException("El correo electrónico '" + paciente.getCorreo() + "' ya está registrado.");
            }
        }

        // 2. Validar para la edición (id != null)
        if (paciente.getId() != null) {
            Optional<Paciente> pacientePorCorreo = repository.porCorreo(paciente.getCorreo());

            // Si el correo ya existe, y el ID asociado a ese correo no es el ID del paciente actual
            if (pacientePorCorreo.isPresent() && !pacientePorCorreo.get().getId().equals(paciente.getId())) {
                throw new CorreoYaExisteException("El correo electrónico '" + paciente.getCorreo() + "' ya está registrado por otro paciente.");
            }
        }

        return repository.guardar(paciente);
    }

    public void eliminar(Integer id) {
        repository.eliminar(id);
    }

    public boolean actualizarEstado(Integer id, Boolean estado) {
        return repository.actualizarEstado(id, estado);
    }
}