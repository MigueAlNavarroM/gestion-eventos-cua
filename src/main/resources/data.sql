-- Datos iniciales para Categoria
INSERT INTO categoria (nombre, descripcion, genera_certificado)
VALUES ('Taller de Música', 'Aprender a tocar guitarra', 1);

INSERT INTO categoria (nombre, descripcion, genera_certificado)
VALUES ('Deportes', 'Actividades deportivas como fútbol y baloncesto', 0);

INSERT INTO categoria (nombre, descripcion, genera_certificado)
VALUES ('Eventos Culturales', 'Charlas, exposiciones y actividades culturales', 0);

-- Datos iniciales para Instructor
INSERT INTO instructor (nombre, apellido_paterno, apellido_materno, correo, telefono, especialidad, bio, activo)
VALUES ('María', 'García', 'Hernández', 'maria.garcia@uam.com', '5559876543', 'Música', 'Especialista en guitarra clásica', 1);

INSERT INTO instructor (nombre, apellido_paterno, apellido_materno, correo, telefono, especialidad, bio, activo)
VALUES ('Carlos', 'Ramírez', 'Santos', 'carlos.ramirez@uam.com', '5551234567', 'Música', 'Profesor de guitarra acústica y eléctrica', 1);

INSERT INTO instructor (nombre, apellido_paterno, apellido_materno, correo, telefono, especialidad, bio, activo)
VALUES ('Luis', 'Fernández', 'Martínez', 'luis.fernandez@uam.com', '5551112233', 'Deportes', 'Entrenador de fútbol y baloncesto', 1);

INSERT INTO instructor (nombre, apellido_paterno, apellido_materno, correo, telefono, especialidad, bio, activo)
VALUES ('Ana', 'López', 'Torres', 'ana.lopez@uam.com', '5552223344', 'Cultura', 'Organizadora de eventos culturales', 1);
