package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.dto.AdminDTO;
import mx.cua.uam.labtem.gestioneventos.entity.AdminEntity;
import mx.cua.uam.labtem.gestioneventos.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admins")
public class AdminController {

    @Autowired
    private AdminService service;

    // Consultar la lista de todos los administradores (formato DTO)
    @GetMapping
    public List<AdminDTO> listar() {
        return service.listar();
    }

    // Consultar un administrador específico por su ID en la URL
    @GetMapping("/{id}")
    public AdminDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    // Registrar un nuevo administrador enviando un JSON en el Body
    @PostMapping
    public AdminDTO crear(@RequestBody AdminEntity a) {
        return service.crear(a);
    }

    // Reemplazar todos los datos de un administrador existente
    @PutMapping("/{id}")
    public AdminDTO actualizar(@PathVariable Integer id, @RequestBody AdminEntity a) {
        return service.actualizar(id, a);
    }

    // Modificar solo campos específicos de un administrador (PATCH)
    @PatchMapping("/{id}")
    public AdminDTO actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> cambios) {
        return service.actualizarParcial(id, cambios);
    }

    // Eliminar un administrador mediante su ID
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}