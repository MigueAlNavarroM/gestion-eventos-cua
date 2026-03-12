package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.entity.CategoriaEntity;
import mx.cua.uam.labtem.gestioneventos.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // CREATE
    @PostMapping
    public CategoriaEntity crear(@RequestBody CategoriaEntity categoria) {
        return categoriaService.crear(categoria);
    }

    // READ - listar todas
    @GetMapping
    public List<CategoriaEntity> listar() {
        return categoriaService.listar();
    }

    // READ - obtener por id
    @GetMapping("/{id}")
    public CategoriaEntity obtener(@PathVariable Integer id) {
        return categoriaService.obtener(id);
    }

    // UPDATE completo
    @PutMapping("/{id}")
    public CategoriaEntity actualizar(@PathVariable Integer id, @RequestBody CategoriaEntity categoria) {
        return categoriaService.actualizar(id, categoria);
    }

    // UPDATE parcial (PATCH)
    @PatchMapping("/{id}")
    public CategoriaEntity actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> cambios) {
        return categoriaService.actualizarParcial(id, cambios);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
    }
}