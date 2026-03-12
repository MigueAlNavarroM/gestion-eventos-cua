package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.model.Instructor;
import mx.cua.uam.labtem.gestioneventos.service.InstructorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructores")
public class InstructorController {

    private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<Instructor> obtenerTodos() {
        return instructorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Instructor> obtenerPorId(@PathVariable Integer id) {
        return instructorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Instructor> crear(@RequestBody Instructor instructor) {
        Instructor nuevoInstructor = instructorService.guardar(instructor);
        return ResponseEntity.ok(nuevoInstructor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Instructor> actualizar(@PathVariable Integer id, @RequestBody Instructor instructor) {
        try {
            Instructor instructorActualizado = instructorService.actualizar(id, instructor);
            return ResponseEntity.ok(instructorActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            instructorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
