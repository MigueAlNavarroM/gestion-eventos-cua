package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.dto.EventoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.EventoEntity;
import mx.cua.uam.labtem.gestioneventos.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eventos")
public class EventoController {
    @Autowired
    private EventoService service;

    @GetMapping
    public List<EventoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public EventoDTO obtener(@PathVariable Integer id) {
        return service.obtener(id);
    }

    @PostMapping
    public EventoDTO crear(@RequestBody EventoEntity e) {
        return service.crear(e);
    }

    @PutMapping("/{id}")
    public EventoDTO actualizar(@PathVariable Integer id, @RequestBody EventoEntity e) {
        return service.actualizar(id, e);
    }

    @PatchMapping("/{id}")
    public EventoDTO actualizarParcial(@PathVariable Integer id, @RequestBody Map<String, Object> cambios) {
        return service.actualizarParcial(id, cambios);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}