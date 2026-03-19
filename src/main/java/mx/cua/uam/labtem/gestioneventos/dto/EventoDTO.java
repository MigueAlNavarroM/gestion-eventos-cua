package mx.cua.uam.labtem.gestioneventos.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoDTO(
        Integer idEvento,
        String titulo,
        String descripcion,
        LocalDate fecha,
        LocalTime hora,
        String lugar,
        String nombreCategoria, // Posición 7
        String nombreInstructor  // <--- AGREGAR ESTO (Posición 8)
) {}