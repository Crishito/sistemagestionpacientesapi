package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.resources;

import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.models.Paciente;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services.PacienteService;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services.exceptions.CedulaYaExisteException;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.services.exceptions.CorreoYaExisteException;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.util.ValidadorCedulaEc;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Optional;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    private PacienteService service;

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZAáéíóú\\s]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");

    private Response validatePaciente(Paciente paciente, boolean isCreation) {

        if (paciente == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Datos de paciente nulos.\"}").build();
        }

        if (isCreation) {
            if (paciente.getCedula() == null || paciente.getCedula().trim().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El campo Cédula es obligatorio.\"}").build();
            }
            if (!ValidadorCedulaEc.esValida(paciente.getCedula())) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"Cédula Ecuatoriana Inválida.\"}").build();
            }
        }

        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El campo Nombre es obligatorio.\"}").build();
        }
        if (!NAME_PATTERN.matcher(paciente.getNombre().trim()).matches()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El Nombre solo debe contener letras.\"}").build();
        }

        if (paciente.getCorreo() == null || paciente.getCorreo().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El campo Correo es obligatorio.\"}").build();
        }
        if (!EMAIL_PATTERN.matcher(paciente.getCorreo().trim()).matches()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El formato del Correo Electrónico es inválido.\"}").build();
        }

        if (paciente.getEdad() == null || paciente.getEdad() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El campo Edad es obligatorio.\"}").build();
        }

        if (paciente.getDireccion() == null || paciente.getDireccion().trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El campo Dirección es obligatorio.\"}").build();
        }

        return null;
    }

    @GET
    public List<Paciente> listar() {
        return service.listar();
    }

    @GET
    @Path("/{id}")
    public Response porId(@PathParam("id") Integer id) {
        return service.porId(id)
                .map(p -> Response.ok(p).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response crear(Paciente paciente) {
        Response validationError = validatePaciente(paciente, true);
        if (validationError != null) {
            return validationError;
        }

        try {
            Paciente nuevoPaciente = service.guardar(paciente);


            Object respuesta = new Object() {
                public final String mensaje = "Paciente registrado correctamente.";
                public final Paciente paciente = nuevoPaciente;
            };

            return Response.status(Response.Status.CREATED).entity(respuesta).build();

        } catch (CedulaYaExisteException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"La cédula '" + paciente.getCedula() + "' ya está registrada.\"}").build();
        } catch (CorreoYaExisteException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\":\"El correo electrónico '" + paciente.getCorreo() + "' ya está registrado.\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error interno al intentar registrar el paciente. Causa: " + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response editar(@PathParam("id") Integer id, Paciente paciente) {
        Response validationError = validatePaciente(paciente, false);
        if (validationError != null) {
            return validationError;
        }

        Optional<Paciente> pacienteExistente = service.porId(id);
        if (pacienteExistente.isPresent()) {
            paciente.setId(id);
            paciente.setCedula(pacienteExistente.get().getCedula());

            try {
                Paciente pacienteActualizado = service.guardar(paciente);
                return Response.ok(pacienteActualizado).build();
            } catch (CedulaYaExisteException e) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\":\"La cédula '" + paciente.getCedula() + "' ya está registrada.\"}").build();
            } catch (CorreoYaExisteException e) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("{\"error\":\"El correo electrónico '" + paciente.getCorreo() + "' ya está registrado por otro paciente.\"}").build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"Error al intentar actualizar el paciente. Causa: " + e.getMessage() + "\"}").build();
            }

        }
        return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Paciente no encontrado para actualizar.\"}").build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        Optional<Paciente> pacienteExistente = service.porId(id);

        if (pacienteExistente.isPresent()) {
            service.eliminar(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Paciente no encontrado para eliminar.\"}").build();
    }

    @PUT
    @Path("/{id}/toggle")
    public Response cambiarEstado(@PathParam("id") Integer id, @QueryParam("activo") Boolean activo) {
        if (activo == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\":\"El parámetro 'activo' es requerido (true o false).\"}").build();
        }

        if (service.porId(id).isPresent()) {
            boolean resultado = service.actualizarEstado(id, activo);

            if (resultado) {
                return service.porId(id)
                        .map(p -> Response.ok(p).build())
                        .orElse(Response.status(Response.Status.INTERNAL_SERVER_ERROR).build());
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("{\"error\":\"Paciente no encontrado para cambio de estado.\"}").build();
    }
}
