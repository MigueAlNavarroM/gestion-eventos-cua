package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.entity.CategoriaEntity;
import mx.cua.uam.labtem.gestioneventos.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaEntity crear(CategoriaEntity categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<CategoriaEntity> listar() {
        return categoriaRepository.findAll();
    }

    public CategoriaEntity obtener(Integer id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    public CategoriaEntity actualizar(Integer id, CategoriaEntity categoria) {
        categoria.setIdCategoria(id);
        return categoriaRepository.save(categoria);
    }

    public CategoriaEntity actualizarParcial(Integer id, Map<String, Object> cambios) {
        CategoriaEntity categoria = obtener(id);
        if (categoria == null) return null;

        if (cambios.containsKey("nombre")) {
            categoria.setNombre((String) cambios.get("nombre"));
        }
        if (cambios.containsKey("descripcion")) {
            categoria.setDescripcion((String) cambios.get("descripcion"));
        }
        if (cambios.containsKey("generaCertificado")) {
            categoria.setGeneraCertificado((Boolean) cambios.get("generaCertificado"));
        }

        return categoriaRepository.save(categoria);
    }

    public void eliminar(Integer id) {
        categoriaRepository.deleteById(id);
    }
}