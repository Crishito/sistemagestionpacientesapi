package com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.repositories;

import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.models.Paciente;
import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.repositories.PacienteRepository;

import com.christian.servidorweb.sistemagestionpacientesapi.sistemagestionpacientesapi.util.Conexion;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class PacienteDAO implements PacienteRepository {


    private Connection getConnection() throws SQLException {

        return Conexion.getConnection();
    }


    @Override
    public List<Paciente> listar() {
        List<Paciente> lista = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM paciente")) {
            while (rs.next()) {
                lista.add(crearPaciente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }


    @Override
    public Optional<Paciente> porId(Integer id) {
        Paciente paciente = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM paciente WHERE id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = crearPaciente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(paciente);
    }

    @Override
    public Optional<Paciente> porCedula(String cedula) {
        Paciente paciente = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM paciente WHERE cedula = ?")) {
            stmt.setString(1, cedula);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = crearPaciente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(paciente);
    }

    @Override
    public Optional<Paciente> porCorreo(String correo) {
        Paciente paciente = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM paciente WHERE correo = ?")) {
            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    paciente = crearPaciente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(paciente);
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        String sql;


        if (paciente.getId() != null && paciente.getId() > 0) {

            sql = "UPDATE paciente SET nombre=?, correo=?, edad=?, direccion=? WHERE id=?";
        } else {

            sql = "INSERT INTO paciente (cedula, nombre, correo, edad, direccion, activo) VALUES (?, ?, ?, ?, ?, ?)";
        }

        try (Connection conn = getConnection();


             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {


            if (paciente.getId() != null && paciente.getId() > 0) {
                // Parámetros para UPDATE
                stmt.setString(1, paciente.getNombre());
                stmt.setString(2, paciente.getCorreo());
                stmt.setInt(3, paciente.getEdad());
                stmt.setString(4, paciente.getDireccion());
                stmt.setInt(5, paciente.getId());

            } else {
                // Parámetros para INSERT
                stmt.setString(1, paciente.getCedula());
                stmt.setString(2, paciente.getNombre());
                stmt.setString(3, paciente.getCorreo());
                stmt.setInt(4, paciente.getEdad());
                stmt.setString(5, paciente.getDireccion());
                stmt.setBoolean(6, true);
            }

            stmt.executeUpdate();


            if (paciente.getId() == null || paciente.getId() <= 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {

                        paciente.setId(rs.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Lanza una excepción para que el PacienteService pueda atraparla, si la DB falla.
            throw new RuntimeException("Error en la base de datos al guardar/actualizar el paciente. Revise su conexión y los campos NOT NULL.", e);
        }
        return paciente;
    }


    @Override
    public void eliminar(Integer id) {

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM paciente WHERE id=?")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Si la eliminación falla, también lanzamos una excepción crítica
            throw new RuntimeException("Error en la base de datos al intentar eliminar el paciente.", e);
        }
    }


    @Override
    public boolean actualizarEstado(Integer id, Boolean activo) {

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE paciente SET activo=? WHERE id=?")) {
            stmt.setBoolean(1, activo);
            stmt.setInt(2, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private Paciente crearPaciente(ResultSet rs) throws SQLException {
        Paciente p = new Paciente();
        p.setId(rs.getInt("id"));
        p.setCedula(rs.getString("cedula"));
        p.setNombre(rs.getString("nombre"));
        p.setCorreo(rs.getString("correo"));
        p.setEdad(rs.getInt("edad"));
        p.setDireccion(rs.getString("direccion"));
        p.setActivo(rs.getBoolean("activo"));
        return p;
    }
}