package mx.cua.uam.labtem.gestioneventos.dto;

public record CategoriaDTO(
        Integer idCategoria,
        String nombre,
        String descripcion,
        Boolean generaCertificado
) {}