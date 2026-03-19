package mx.cua.uam.labtem.gestioneventos.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record InscripcionDTO(
        Integer idInscripcion,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fechaInscripcion,
        String nombreAlumno,
        String tituloEvento,
        Integer idAlumno,
        Integer idEvento
) {}