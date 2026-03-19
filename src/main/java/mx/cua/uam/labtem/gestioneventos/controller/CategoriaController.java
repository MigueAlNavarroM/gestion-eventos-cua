package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.dto.CategoriaDTO;
import mx.cua.uam.labtem.gestioneventos.entity.CategoriaEntity;
import mx.cua.uam.labtem.gestioneventos.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*") // Esto es lo único que agregamos para que tu JS funcione
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    // 1. Listar todas
    @GetMapping
    public List<CategoriaDTO> listar() {
        return service.listar();
    }

    // 2. Obtener por ID (El que usa tu buscador de Bootstrap)
    @GetMapping("/{id}")
    public CategoriaDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    // 3. Crear nueva categoría
    @PostMapping
    public CategoriaDTO crear(@RequestBody CategoriaEntity c) {
        return service.crear(c);
    }

    // 4. Actualizar completa
    @PutMapping("/{id}")
    public CategoriaDTO actualizar(@PathVariable Integer id, @RequestBody CategoriaEntity c) {
        return service.actualizar(id, c);
    }

    // 5. Actualizar parcial
    @PatchMapping("/{id}")
    public CategoriaDTO actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> cambios) {
        return service.actualizarParcial(id, cambios);
    }

    // 6. Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}