package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.dto.InscripcionDTO;
import mx.cua.uam.labtem.gestioneventos.entity.InscripcionEntity;
import mx.cua.uam.labtem.gestioneventos.entity.EventoEntity;
import mx.cua.uam.labtem.gestioneventos.entity.AlumnoEntity;
import mx.cua.uam.labtem.gestioneventos.repository.InscripcionRepository;
import mx.cua.uam.labtem.gestioneventos.repository.EventoRepository;
import mx.cua.uam.labtem.gestioneventos.repository.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionService {

    @Autowired
    private InscripcionRepository repo;

    @Autowired
    private EventoRepository eventoRepo;

    @Autowired
    private AlumnoRepository alumnoRepo;

    // Convertir de Entidad a DTO para que el Controller no marque error
    private InscripcionDTO convertirADTO(InscripcionEntity e) {
        return new InscripcionDTO(
                e.getIdInscripcion(),
                e.getFechaRegistro(), // Usamos el nombre correcto de tu Entity
                e.getAlumno().getNombre(),
                e.getEvento().getTitulo(),
                e.getAlumno().getIdAlumno(),
                e.getEvento().getIdEvento()
        );
    }

    public List<InscripcionDTO> listar() {
        return repo.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    /**
     * Lógica para inscribir a un alumno usando el DTO que viene de Postman
     */
    public InscripcionDTO crear(InscripcionDTO dto) {
        // 1. Verificar existencia
        AlumnoEntity alumno = alumnoRepo.findById(dto.idAlumno()).orElse(null);
        EventoEntity evento = eventoRepo.findById(dto.idEvento()).orElse(null);

        if (alumno != null && evento != null) {
            // 2. Evitar duplicados (Vanessa no quiere alumnos repetidos en su taller)
            if (repo.existsByAlumnoIdAlumnoAndEventoIdEvento(dto.idAlumno(), dto.idEvento())) {
                return null;
            }

            InscripcionEntity nueva = new InscripcionEntity();
            nueva.setAlumno(alumno);
            nueva.setEvento(evento);
            // La fecha se pone sola gracias al @PrePersist que ya tienes en la Entity

            return convertirADTO(repo.save(nueva));
        }

        return null;
    }

    public void eliminar(Integer id) {
        repo.deleteById(id);
    }
}