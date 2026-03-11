package mx.cua.uam.labtem.gestioneventos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoriaController {

    @GetMapping("/categoria")
    public String obtenerCategoria() {
        return "Controller de Categoria funcionando";
    }

}
