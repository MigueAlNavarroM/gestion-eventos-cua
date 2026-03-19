package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.dto.InstructorDTO;
import mx.cua.uam.labtem.gestioneventos.dto.EventoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.InstructorEntity;
import mx.cua.uam.labtem.gestioneventos.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository repo;

    /**
     * Obtener la lista de eventos vinculados a un instructor específico.
     * Corregido para coincidir con los 8 parámetros del EventoDTO.
     */
    public List<EventoDTO> listarEventosPorInstructor(Integer id) {
        InstructorEntity ins = repo.findById(id).orElse(null);
        if (ins == null) return null;

        // Preparamos el nombre completo del instructor para el DTO
        String nombreCompleto = ins.getNombre() + " " + ins.getApellidoPaterno();

        return ins.getEventos().stream()
                .map(e -> new EventoDTO(
                        e.getIdEvento(),
                        e.getTitulo(),
                        e.getDescripcion(),
                        e.getFecha(),
                        e.getHora(),
                        e.getLugar(),
                        e.getCategoria() != null ? e.getCategoria().getNombre() : "Sin Categoría",
                        nombreCompleto // <--- Dato 8: nombreInstructor
                ))
                .collect(Collectors.toList());
    }

    private InstructorDTO convertirADTO(InstructorEntity i) {
        return new InstructorDTO(
                i.getIdInstructor(),
                i.getNombre(),
                i.getApellidoPaterno(),
                i.getApellidoMaterno(),
                i.getCorreo(),
                i.getTelefono(),
                i.getEspecialidad(),
                i.getBio(),
                i.getActivo()
        );
    }

    public List<InstructorDTO> listar() {
        return repo.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public InstructorDTO obtener(Integer id) {
        return repo.findById(id).map(this::convertirADTO).orElse(null);
    }

    public InstructorDTO crear(InstructorEntity i) {
        return convertirADTO(repo.save(i));
    }

    public InstructorDTO actualizar(Integer id, InstructorEntity i) {
        InstructorEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        ex.setNombre(i.getNombre());
        ex.setApellidoPaterno(i.getApellidoPaterno());
        ex.setApellidoMaterno(i.getApellidoMaterno());
        ex.setCorreo(i.getCorreo());
        ex.setTelefono(i.getTelefono());
        ex.setEspecialidad(i.getEspecialidad());
        ex.setBio(i.getBio());
        ex.setActivo(i.getActivo());
        return convertirADTO(repo.save(ex));
    }

    public InstructorDTO actualizarParcial(Integer id, Map<String, Object> cambios) {
        InstructorEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        if (cambios.containsKey("nombre")) ex.setNombre((String) cambios.get("nombre"));
        if (cambios.containsKey("correo")) ex.setCorreo((String) cambios.get("correo"));
        if (cambios.containsKey("activo")) ex.setActivo((Boolean) cambios.get("activo"));
        return convertirADTO(repo.save(ex));
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}