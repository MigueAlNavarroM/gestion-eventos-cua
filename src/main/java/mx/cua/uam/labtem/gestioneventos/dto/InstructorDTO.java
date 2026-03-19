package mx.cua.uam.labtem.gestioneventos.dto;

public record InstructorDTO(
        Integer idInstructor,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String correo,
        String telefono,
        String especialidad,
        String bio,
        Boolean activo
) {}