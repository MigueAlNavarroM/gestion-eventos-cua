package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.dto.InscripcionDTO;
import mx.cua.uam.labtem.gestioneventos.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    @Autowired
    private InscripcionService service;

    @GetMapping
    public List<InscripcionDTO> listar() {
        return service.listar();
    }

    @PostMapping
    public InscripcionDTO crear(@RequestBody InscripcionDTO dto) {
        return service.crear(dto);
    }
}