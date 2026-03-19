-- Limpieza de datos previos
--SET FOREIGN_KEY_CHECKS = 0;
--TRUNCATE TABLE instructor_categoria;
--TRUNCATE TABLE instructor;
--TRUNCATE TABLE categoria;
--SET FOREIGN_KEY_CHECKS = 1;

    -- 2. CATEGORÍAS
-- Solo el ID 1 (Taller) tiene genera_certificado = 1
INSERT INTO categoria (id_categoria, nombre, descripcion, genera_certificado)
VALUES (1, 'Taller de Música', 'Sesiones prácticas que otorgan diploma', 1),
       (2, 'Deportes', 'Actividades recreativas sin certificado', 0),
       (3, 'Eventos Culturales', 'Charlas y exposiciones generales', 0);

-- 3. INSTRUCTORES
INSERT INTO instructor (id_instructor, nombre, apellido_paterno, apellido_materno, correo, telefono, especialidad, bio, activo)
VALUES (1, 'María', 'García', 'Hernández', 'maria.garcia@uam.com', '5559876543', 'Música', 'Especialista en guitarra clásica', 1),
       (2, 'Carlos', 'Ramírez', 'Santos', 'carlos.ramirez@uam.com', '5551234567', 'Música', 'Profesor de cuerdas eléctricas', 1),
       (3, 'Luis', 'Fernández', 'Martínez', 'luis.fernandez@uam.com', '5551112233', 'Deportes', 'Entrenador de básquetbol', 1);

-- 4. RELACIÓN INSTRUCTOR-CATEGORÍA
INSERT INTO instructor_categoria (id_instructor, id_categoria)
VALUES (1, 1), (2, 1), (3, 2);

-- 5. ALUMNOS
INSERT INTO alumno (id_alumno, nombre, correo, matricula, password_hash)
VALUES (1, 'Juan Pérez', 'juan.perez@uam.com', '22130001', 'argon2_hash_j1'),
       (2, 'Sofía Reyes', 'sofia.reyes@uam.com', '22130002', 'argon2_hash_s2');

-- 6. ADMINISTRADORES
INSERT INTO administrador (id_admin, nombre, correo, password_hash)
VALUES (1, 'Soporte Técnico', 'soporte.labtem@uam.com', 'admin_secure_pass');

-- 7. EVENTOS
-- El evento 1 es un Taller (genera certificado), el evento 2 es Deporte (no genera)
INSERT INTO evento (id_evento, titulo, descripcion, fecha, hora, lugar, id_categoria)
VALUES (1, 'Taller de Guitarra Pro', 'Práctica intensiva de acordes', '2026-04-15', '10:00:00', 'Aula 102', 1),
       (2, 'Torneo Fútbol CUA', 'Competencia interna', '2026-05-10', '09:00:00', 'Canchas Centrales', 2);

-- 8. INSCRIPCIONES
INSERT INTO inscripcion (id_inscripcion, fecha_registro, id_alumno, id_evento, inscripto)
VALUES (1, '2026-03-01 09:00:00', 1, 1, 1), -- Juan se inscribió al Taller
       (2, '2026-03-01 10:30:00', 2, 2, 1); -- Sofía se inscribió al Torneo

-- 9. CERTIFICADOS
-- Solo creamos el certificado para la Inscripción 1 (que es del Taller de Música)
INSERT INTO certificado (folio, fecha_emision, archivo, id_inscripcion)
VALUES ('UAM-CUA-MUS-2026-001', '2026-03-15 12:00:00', 'cert_juan_guitarra.pdf', 1);