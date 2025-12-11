-- Creación de la tabla Paciente
CREATE TABLE paciente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE NOT NULL,
    edad INT,
    direccion VARCHAR(255),
    cedula VARCHAR(10) UNIQUE NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Datos de prueba
INSERT INTO paciente (nombre, correo, edad, direccion, cedula) VALUES
('Cristian Zumárraga', 'crishito@test.com', 27, 'La Roca', '1712345678');

select * from paciente;

