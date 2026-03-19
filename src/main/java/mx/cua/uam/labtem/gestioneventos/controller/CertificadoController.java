package mx.cua.uam.labtem.gestioneventos.controller;

import mx.cua.uam.labtem.gestioneventos.dto.CertificadoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.CertificadoEntity;
import mx.cua.uam.labtem.gestioneventos.service.CertificadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificados")
public class CertificadoController {

    @Autowired
    private CertificadoService service;

    @GetMapping
    public List<CertificadoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{folio}")
    public CertificadoDTO obtener(@PathVariable String folio) {
        return service.obtener(folio);
    }

    // Generar nuevo (POST)
    @PostMapping("/generar/{idInscripcion}")
    public CertificadoDTO crear(@PathVariable Integer idInscripcion) {
        return service.crearDesdeInscripcion(idInscripcion);
    }

    // Actualizar todo (PUT)
    @PutMapping("/{folio}")
    public CertificadoDTO actualizar(@PathVariable String folio, @RequestBody CertificadoEntity c) {
        return service.actualizar(folio, c);
    }

    // Actualizar solo archivo (PATCH)
    @PatchMapping("/{folio}/archivo")
    public CertificadoDTO patchArchivo(@PathVariable String folio, @RequestBody String nuevoNombre) {
        return service.actualizarArchivo(folio, nuevoNombre);
    }

    @DeleteMapping("/{folio}")
    public void eliminar(@PathVariable String folio) {
        service.eliminar(folio);
    }
}