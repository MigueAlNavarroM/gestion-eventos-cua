package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.dto.CategoriaDTO;
import mx.cua.uam.labtem.gestioneventos.entity.CategoriaEntity;
import mx.cua.uam.labtem.gestioneventos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository repo;

    // Método privado para convertir la entidad interna a un DTO de salida
    private CategoriaDTO convertirADTO(CategoriaEntity c) {
        return new CategoriaDTO(c.getIdCategoria(), c.getNombre(), c.getDescripcion(), c.getGeneraCertificado());
    }

    public List<CategoriaDTO> listar() {
        return repo.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public CategoriaDTO obtener(Integer id) {
        return repo.findById(id).map(this::convertirADTO).orElse(null);
    }

    public CategoriaDTO crear(CategoriaEntity c) { return convertirADTO(repo.save(c)); }

    // PUT: Reemplaza todos los valores de la categoría
    public CategoriaDTO actualizar(Integer id, CategoriaEntity c) {
        CategoriaEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        ex.setNombre(c.getNombre());
        ex.setDescripcion(c.getDescripcion());
        ex.setGeneraCertificado(c.getGeneraCertificado());
        return convertirADTO(repo.save(ex));
    }

    // PATCH: Actualiza solo campos específicos dinámicamente
    public CategoriaDTO actualizarParcial(Integer id, Map<String, Object> cambios) {
        CategoriaEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        if (cambios.containsKey("nombre")) ex.setNombre((String) cambios.get("nombre"));
        if (cambios.containsKey("descripcion")) ex.setDescripcion((String) cambios.get("descripcion"));
        if (cambios.containsKey("generaCertificado")) ex.setGeneraCertificado((Boolean) cambios.get("generaCertificado"));
        return convertirADTO(repo.save(ex));
    }

    public void eliminar(Integer id) { repo.deleteById(id); }
}