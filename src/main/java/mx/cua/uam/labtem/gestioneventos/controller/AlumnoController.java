package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.dto.AlumnoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.AlumnoEntity;
import mx.cua.uam.labtem.gestioneventos.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {
    @Autowired
    private AlumnoService service;

    @GetMapping // Obtener todos
    public List<AlumnoDTO> listar() { return service.listar(); }

    @GetMapping("/{id}") // Obtener uno por ID
    public AlumnoDTO obtener(@PathVariable Integer id) { return service.obtener(id); }

    @PostMapping // Crear nuevo
    public AlumnoDTO crear(@RequestBody AlumnoEntity a) { return service.crear(a); }

    @PutMapping("/{id}") // Reemplazar todo el objeto
    public AlumnoDTO actualizar(@PathVariable Integer id, @RequestBody AlumnoEntity a) {
        return service.actualizar(id, a);
    }

    @PatchMapping("/{id}") // Modificar campos específicos
    public AlumnoDTO actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> cambios) {
        return service.actualizarParcial(id, cambios);
    }

    @DeleteMapping("/{id}") // Borrar registro
    public void eliminar(@PathVariable Integer id) { service.eliminar(id); }
}
