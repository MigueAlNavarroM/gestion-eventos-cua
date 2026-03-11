/* =========================================================
   SISTEMA DE GESTIÓN DE EVENTOS CULTURALES - UAM CUAJIMALPA
   Archivo: gestion_eventos_cua.sql
   Motor: MySQL 8+
   ========================================================= */

/* =========================================================
   1) OPCIONAL: ELIMINAR BASE DE DATOS
   Descomenta solo si quieres borrar todo desde cero
   ========================================================= */
-- DROP DATABASE IF EXISTS gestion_eventos_cua;

/* =========================================================
   2) OPCIONAL: CREAR BASE DE DATOS
   Descomenta si aún no existe
   ========================================================= */
-- CREATE DATABASE IF NOT EXISTS gestion_eventos_cua
-- CHARACTER SET utf8mb4
-- COLLATE utf8mb4_unicode_ci;

/* =========================================================
   3) USAR BASE DE DATOS
   ========================================================= */
USE gestion_eventos_cua;

/* =========================================================
   4) OPCIONAL: ELIMINAR TABLAS EN ORDEN SEGURO
   Descomenta si quieres reconstruir solo las tablas
   ========================================================= */
-- SET FOREIGN_KEY_CHECKS = 0;
-- DROP TABLE IF EXISTS certificado;
-- DROP TABLE IF EXISTS inscripcion;
-- DROP TABLE IF EXISTS evento_instructor;
-- DROP TABLE IF EXISTS evento;
-- DROP TABLE IF EXISTS instructor;
-- DROP TABLE IF EXISTS categoria;
-- DROP TABLE IF EXISTS alumno;
-- DROP TABLE IF EXISTS admin;
-- SET FOREIGN_KEY_CHECKS = 1;

/* =========================================================
   5) TABLA: admin
   Administradores del sistema
   No participan en inscripciones ni certificados
   ========================================================= */
CREATE TABLE IF NOT EXISTS admin (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   6) TABLA: alumno
   Alumnos que se inscriben a eventos
   ========================================================= */
CREATE TABLE IF NOT EXISTS alumno (
    id_alumno INT AUTO_INCREMENT PRIMARY KEY,
    matricula VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    carrera VARCHAR(150) NULL,
    trimestre INT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   7) TABLA: categoria
   Cada evento pertenece a una categoría
   genera_certificado = 1 si esa categoría permite certificados
   Ejemplo: Taller = sí, Concierto = no
   ========================================================= */
CREATE TABLE IF NOT EXISTS categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(255) NULL,
    genera_certificado TINYINT(1) NOT NULL DEFAULT 0,
    activa TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   8) TABLA: instructor
   Instructores que pueden impartir uno o varios eventos
   ========================================================= */
CREATE TABLE IF NOT EXISTS instructor (
    id_instructor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100) NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20) NULL,
    especialidad VARCHAR(150) NULL,
    bio VARCHAR(500) NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   9) TABLA: evento
   Cada evento pertenece a una categoría
   ========================================================= */
CREATE TABLE IF NOT EXISTS evento (
    id_evento INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT NULL,
    cupo_maximo INT NOT NULL,
    fecha_evento DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fin TIME NOT NULL,
    lugar VARCHAR(150) NOT NULL,
    disponible TINYINT(1) NOT NULL DEFAULT 1,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT chk_evento_cupo_maximo CHECK (cupo_maximo > 0),
    CONSTRAINT chk_evento_horas CHECK (hora_fin > hora_inicio),

    CONSTRAINT fk_evento_categoria
        FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   10) TABLA: evento_instructor
   Tabla puente para resolver la relación muchos a muchos
   entre evento e instructor
   ========================================================= */
CREATE TABLE IF NOT EXISTS evento_instructor (
    id_evento_instructor INT AUTO_INCREMENT PRIMARY KEY,
    id_evento INT NOT NULL,
    id_instructor INT NOT NULL,
    rol VARCHAR(100) NULL,
    fecha_asignacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_evento_instructor UNIQUE (id_evento, id_instructor),

    CONSTRAINT fk_evento_instructor_evento
        FOREIGN KEY (id_evento)
        REFERENCES evento(id_evento)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_evento_instructor_instructor
        FOREIGN KEY (id_instructor)
        REFERENCES instructor(id_instructor)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   11) TABLA: inscripcion
   Un alumno puede inscribirse a varios eventos
   Pero no puede inscribirse dos veces al mismo evento
   ========================================================= */
CREATE TABLE IF NOT EXISTS inscripcion (
    id_inscripcion INT AUTO_INCREMENT PRIMARY KEY,
    id_alumno INT NOT NULL,
    id_evento INT NOT NULL,
    inscrito TINYINT(1) NOT NULL DEFAULT 1,
    fecha_inscripcion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_inscripcion_alumno_evento UNIQUE (id_alumno, id_evento),

    CONSTRAINT fk_inscripcion_alumno
        FOREIGN KEY (id_alumno)
        REFERENCES alumno(id_alumno)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_inscripcion_evento
        FOREIGN KEY (id_evento)
        REFERENCES evento(id_evento)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   12) TABLA: certificado
   Cada inscripción puede generar solo un certificado
   Relación 1 a 1 con inscripcion
   ========================================================= */
CREATE TABLE IF NOT EXISTS certificado (
    id_certificado INT AUTO_INCREMENT PRIMARY KEY,
    id_inscripcion INT NOT NULL,
    folio VARCHAR(50) NOT NULL UNIQUE,
    archivo_url VARCHAR(255) NULL,
    fecha_emision TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT uq_certificado_inscripcion UNIQUE (id_inscripcion),

    CONSTRAINT fk_certificado_inscripcion
        FOREIGN KEY (id_inscripcion)
        REFERENCES inscripcion(id_inscripcion)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/* =========================================================
   13) ÍNDICES ÚTILES
   ========================================================= */
CREATE INDEX idx_evento_categoria ON evento(id_categoria);
CREATE INDEX idx_evento_fecha ON evento(fecha_evento);

CREATE INDEX idx_evento_instructor_evento ON evento_instructor(id_evento);
CREATE INDEX idx_evento_instructor_instructor ON evento_instructor(id_instructor);

CREATE INDEX idx_inscripcion_alumno ON inscripcion(id_alumno);
CREATE INDEX idx_inscripcion_evento ON inscripcion(id_evento);
CREATE INDEX idx_certificado_folio ON certificado(folio);

/* =========================================================
   14) DATOS MÍNIMOS OPCIONALES DE PRUEBA
   Descomenta solo si quieres insertar ejemplos
   ========================================================= */

-- INSERT INTO categoria (nombre, descripción, genera_certificado)
-- VALUES
-- ('Taller', 'Actividades prácticas con instructor', 1),
-- ('Concierto', 'Presentaciones musicales o artísticas', 0),
-- ('Exposición', 'Muestras culturales y visuales', 0);

-- INSERT INTO admin (nombre, apellido_paterno, apellido_materno, correo, password_hash)
-- VALUES
-- ('Administrador', 'General', NULL, 'admin@uam.mx', 'HASH_AQUÍ');

-- INSERT INTO alumno (matrícula, nombre, apellido_paterno, apellido_materno, correo, password_hash, carrera, trimestre)
-- VALUES
-- ('220000001', 'Miguel', 'Navarro', 'Moreno', 'miguel@alumnos.uam.mx', 'HASH_AQUÍ', 'Tecnologías y Sistemas de Información', 10);

-- INSERT INTO instructor (nombre, apellido_paterno, apellido_materno, correo, teléfono, especialidad, bio)
-- VALUES
-- ('Ana', 'López', 'Ramírez', 'ana.lopez@uam.mx', '5512345678', 'Arte y cultura', 'Instructora de talleres culturales');

-- INSERT INTO evento (id_categoria, titulo, descripción, cupo_maximo, fecha_evento, hora_inicio, hora_fin, lugar, disponible)
-- VALUES
-- (1, 'Taller de Fotografía', 'Taller introductorio de fotografía cultural', 30, '2026-04-15', '10:00:00', '13:00:00', 'Sala Cultural 1', 1);

-- INSERT INTO evento_instructor (id_evento, id_instructor, rol)
-- VALUES
-- (1, 1, 'Instructor principal');

-- INSERT INTO inscripcion (id_alumno, id_evento, inscrito)
-- VALUES
-- (1, 1, 1);

-- INSERT INTO certificado (id_inscripcion, folio, archivo_url)
-- VALUES
-- (1, 'CERT-2026-0001', '/certificados/CERT-2026-0001.pdf');

/* =========================================================
   15) CONSULTAS RÁPIDAS OPCIONALES
   ========================================================= */

-- Ver todas las categorías
-- SELECT * FROM categoria;

-- Ver todos los eventos con su categoría
-- SELECT e.id_evento, e.titulo, c.nombre AS categoria, e.fecha_evento, e.lugar
-- FROM evento e
-- INNER JOIN categoria c ON e.id_categoria = c.id_categoria;

-- Ver instructores asignados a eventos
-- SELECT e.titulo, i.nombre, i.apellido_paterno, ei.rol
-- FROM evento_instructor ei
-- INNER JOIN evento e ON ei.id_evento = e.id_evento
-- INNER JOIN instructor i ON ei.id_instructor = i.id_instructor;

-- Ver inscripciones de alumnos
-- SELECT ins.id_inscripcion, a.nombre, a.apellido_paterno, e.titulo, ins.fecha_inscripcion
-- FROM inscripcion ins
-- INNER JOIN alumno a ON ins.id_alumno = a.id_alumno
-- INNER JOIN evento e ON ins.id_evento = e.id_evento;

-- Ver certificados emitidos
-- SELECT c.id_certificado, c.folio, c.fecha_emisión, a.nombre, e.titulo
-- FROM certificado c
-- INNER JOIN inscripcion i ON c.id_inscripcion = i.id_inscripcion
-- INNER JOIN alumno a ON i.id_alumno = a.id_alumno
-- INNER JOIN evento e ON i.id_evento = e.id_evento;