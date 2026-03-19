package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.dto.EventoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.EventoEntity;
import mx.cua.uam.labtem.gestioneventos.entity.InstructorEntity;
import mx.cua.uam.labtem.gestioneventos.repository.CategoriaRepository;
import mx.cua.uam.labtem.gestioneventos.repository.EventoRepository;
import mx.cua.uam.labtem.gestioneventos.repository.InstructorRepository; // Asegúrate de tener este repo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Autowired
    private InstructorRepository instructorRepo;

    // Método para transformar la Entidad en el DTO que ve el usuario
    private EventoDTO convertirADTO(EventoEntity e) {
        String nombreCat = (e.getCategoria() != null) ? e.getCategoria().getNombre() : "Sin Categoría";

        // Lógica para mostrar al menos al instructor principal en el DTO
        String nombreInst = "Sin Instructor";
        if (e.getInstructores() != null && !e.getInstructores().isEmpty()) {
            InstructorEntity primero = e.getInstructores().get(0);
            nombreInst = primero.getNombre() + " " + primero.getApellidoPaterno();
        }

        // Dentro de convertirADTO en EventoService.java
        return new EventoDTO(
                e.getIdEvento(),
                e.getTitulo(),
                e.getDescripcion(),
                e.getFecha(),
                e.getHora(),
                e.getLugar(),
                nombreCat,
                nombreInst
        );
    }

    public List<EventoDTO> listar() {
        return repo.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public EventoDTO obtener(Integer id) {
        return repo.findById(id).map(this::convertirADTO).orElse(null);
    }

    public EventoDTO crear(EventoEntity e) {
        return convertirADTO(repo.save(e));
    }

    public EventoDTO actualizar(Integer id, EventoEntity e) {
        EventoEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;

        ex.setTitulo(e.getTitulo());
        ex.setDescripcion(e.getDescripcion());
        ex.setLugar(e.getLugar());
        ex.setFecha(e.getFecha());
        ex.setHora(e.getHora());

        if (e.getCategoria() != null) ex.setCategoria(e.getCategoria());
        if (e.getInstructores() != null) ex.setInstructores(e.getInstructores());

        return convertirADTO(repo.save(ex));
    }

    @SuppressWarnings("unchecked")
    public EventoDTO actualizarParcial(Integer id, Map<String, Object> cambios) {
        EventoEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;

        // Actualización de campos básicos
        if (cambios.containsKey("titulo")) ex.setTitulo((String) cambios.get("titulo"));
        if (cambios.containsKey("descripcion")) ex.setDescripcion((String) cambios.get("descripcion"));
        if (cambios.containsKey("lugar")) ex.setLugar((String) cambios.get("lugar"));
        if (cambios.containsKey("fecha")) ex.setFecha(LocalDate.parse((String) cambios.get("fecha")));
        if (cambios.containsKey("hora")) ex.setHora(LocalTime.parse((String) cambios.get("hora")));

        // Lógica para actualizar la CATEGORÍA
        if (cambios.containsKey("categoria")) {
            Map<String, Object> catMap = (Map<String, Object>) cambios.get("categoria");
            if (catMap.containsKey("idCategoria")) {
                Integer idCat = (Integer) catMap.get("idCategoria");
                categoriaRepo.findById(idCat).ifPresent(ex::setCategoria);
            }
        }

        // Lógica para actualizar el INSTRUCTOR (Vanessa ID: 5)
        if (cambios.containsKey("instructor")) {
            Map<String, Object> instMap = (Map<String, Object>) cambios.get("instructor");
            if (instMap.containsKey("idInstructor")) {
                Integer idInst = (Integer) instMap.get("idInstructor");
                instructorRepo.findById(idInst).ifPresent(nuevoInst -> {
                    // Como es @ManyToMany, limpiamos la lista vieja y agregamos al nuevo
                    List<InstructorEntity> lista = new ArrayList<>();
                    lista.add(nuevoInst);
                    ex.setInstructores(lista);
                });
            }
        }

        return convertirADTO(repo.save(ex));
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}