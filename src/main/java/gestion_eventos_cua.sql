CREATE DATABASE gestion_eventos_cua;
USE gestion_eventos_cua;

-- Tabla Alumno
CREATE TABLE alumno (
    id_alumno INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) UNIQUE NOT NULL,
    correo VARCHAR(120) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

-- Tabla Admin
CREATE TABLE admin (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(120) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);

-- Tabla Categoría
CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(255),
    genera_certificado BOOLEAN DEFAULT FALSE
);

-- Tabla Evento (se eliminó campo 'activo')
CREATE TABLE evento (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    lugar VARCHAR(150),
    id_categoria INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

-- Tabla Instructor (quienes imparten talleres/eventos)
CREATE TABLE instructor (
    id_instructor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100),
    correo VARCHAR(120) UNIQUE
);

-- Relación muchos a muchos Evento ↔ Instructor
CREATE TABLE evento_instructor (
    id_evento INT NOT NULL,
    id_instructor INT NOT NULL,
    PRIMARY KEY (id_evento, id_instructor),
    FOREIGN KEY (id_evento) REFERENCES evento(id_evento),
    FOREIGN KEY (id_instructor) REFERENCES instructor(id_instructor)
);

-- Tabla Inscripción (estado reemplazado por booleano 'inscripto')
CREATE TABLE inscripcion (
    id_inscripcion INT AUTO_INCREMENT PRIMARY KEY,
    id_evento INT NOT NULL,
    id_alumno INT NOT NULL,
    inscripto BOOLEAN DEFAULT TRUE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_evento) REFERENCES evento(id_evento),
    FOREIGN KEY (id_alumno) REFERENCES alumno(id_alumno)
);

-- Tabla Certificado (incluye archivo, relación 1 a 1 con inscripción)
CREATE TABLE certificado (
    folio VARCHAR(40) PRIMARY KEY,
    fecha_emision TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    archivo VARCHAR(255) NOT NULL, -- ruta o nombre del archivo PDF
    id_inscripcion INT UNIQUE NOT NULL,
    FOREIGN KEY (id_inscripcion) REFERENCES inscripcion(id_inscripcion)
);