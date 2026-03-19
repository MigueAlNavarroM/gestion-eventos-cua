package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.dto.InstructorDTO;
import mx.cua.uam.labtem.gestioneventos.dto.EventoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.InstructorEntity;
import mx.cua.uam.labtem.gestioneventos.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService service;

    @GetMapping
    public List<InstructorDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public InstructorDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    // URL: http://localhost:8080/instructores/{id}/eventos
    @GetMapping("/{id}/eventos")
    public List<EventoDTO> obtenerEventos(@PathVariable Integer id) {
        List<EventoDTO> eventos = service.listarEventosPorInstructor(id);
        return eventos != null ? eventos : java.util.Collections.emptyList();
    }

    @PostMapping
    public InstructorDTO crear(@RequestBody InstructorEntity i) {
        return service.crear(i);
    }

    @PutMapping("/{id}")
    public InstructorDTO actualizar(@PathVariable Integer id, @RequestBody InstructorEntity i) {
        return service.actualizar(id, i);
    }

    @PatchMapping("/{id}")
    public InstructorDTO actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> cambios) {
        return service.actualizarParcial(id, cambios);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}