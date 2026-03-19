package mx.cua.uam.labtem.gestioneventos.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CertificadoDTO(
        String folio,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime fechaEmision,
        String archivo,
        String nombreAlumno,
        String nombreEvento,
        Integer idInscripcion
) {}