package mx.cua.uam.labtem.gestioneventos.dto;

public record AlumnoDTO(
        Integer idAlumno,
        String nombre,
        String correo,
        String matricula
        // Seguridad: El passwordHash queda fuera del DTO
) {}