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

-- Datos de prueba opcionales
INSERT INTO paciente (nombre, correo, edad, direccion, cedula) VALUES
('Juan Pérez', 'juan.perez@test.com', 35, 'Av. Siempre Viva 123', '1712345678'); 

-- Nota: Reemplaza con una cédula ecuatoriana válida