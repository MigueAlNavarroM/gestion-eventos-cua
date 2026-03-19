package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.dto.AdminDTO;
import mx.cua.uam.labtem.gestioneventos.entity.AdminEntity;
import mx.cua.uam.labtem.gestioneventos.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    // Convierte la entidad de la base de datos al Record DTO para salida segura
    private AdminDTO convertirADTO(AdminEntity a) {
        return new AdminDTO(a.getIdAdmin(), a.getNombre(), a.getCorreo());
    }

    // Obtener todos los administradores mapeados a DTO
    public List<AdminDTO> listar() {
        return repo.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Buscar un admin por su ID y devolverlo como DTO
    public AdminDTO obtener(Integer id) {
        return repo.findById(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    // Guardar un nuevo administrador y retornar su DTO
    public AdminDTO crear(AdminEntity a) {
        return convertirADTO(repo.save(a));
    }

    // Actualización completa de los datos del administrador
    public AdminDTO actualizar(Integer id, AdminEntity a) {
        AdminEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        ex.setNombre(a.getNombre());
        ex.setCorreo(a.getCorreo());
        return convertirADTO(repo.save(ex));
    }

    // Actualización parcial (PATCH) para modificar solo campos específicos
    public AdminDTO actualizarParcial(Integer id, Map<String, Object> cambios) {
        AdminEntity ex = repo.findById(id).orElse(null);
        if (ex == null) return null;
        if (cambios.containsKey("nombre")) ex.setNombre((String) cambios.get("nombre"));
        if (cambios.containsKey("correo")) ex.setCorreo((String) cambios.get("correo"));
        return convertirADTO(repo.save(ex));
    }

    // Eliminar definitivamente a un administrador por ID
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}