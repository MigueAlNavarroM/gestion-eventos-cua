package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.dto.AlumnoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.AlumnoEntity;
import mx.cua.uam.labtem.gestioneventos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlumnoService {
    @Autowired
    private AlumnoRepository repo;

    // Mapeo a DTO para no exponer la contraseña (passwordHash)
    private AlumnoDTO convertirADTO(AlumnoEntity a) {
        return new AlumnoDTO(a.getIdAlumno(), a.getNombre(), a.getCorreo(), a.getMatricula());
    }

    public List<AlumnoDTO> listar() {
        return repo.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public AlumnoDTO obtener(Integer id) {
        return repo.findById(id).map(this::convertirADTO).orElse(null);
    }

    public AlumnoDTO crear(AlumnoEntity a) { return convertirADTO(repo.save(a)); }

    // PUT: Actualización total del registro
    public AlumnoDTO actualizar(Integer id, AlumnoEntity a) {
        AlumnoEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        ex.setNombre(a.getNombre());
        ex.setCorreo(a.getCorreo());
        ex.setMatricula(a.getMatricula());
        if(a.getPasswordHash() != null) ex.setPasswordHash(a.getPasswordHash());
        return convertirADTO(repo.save(ex));
    }

    // PATCH: Actualización parcial (solo campos enviados)
    public AlumnoDTO actualizarParcial(Integer id, Map<String, Object> cambios) {
        AlumnoEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        if (cambios.containsKey("nombre")) ex.setNombre((String) cambios.get("nombre"));
        if (cambios.containsKey("correo")) ex.setCorreo((String) cambios.get("correo"));
        return convertirADTO(repo.save(ex));
    }

    public void eliminar(Integer id) { repo.deleteById(id); }
}