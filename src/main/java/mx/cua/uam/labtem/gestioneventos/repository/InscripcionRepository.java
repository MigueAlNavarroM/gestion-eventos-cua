package mx.cua.uam.labtem.gestioneventos.repository;

import mx.cua.uam.labtem.gestioneventos.entity.InscripcionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Integer> {
    List<InscripcionEntity> findByAlumnoIdAlumno(Integer idAlumno);
    List<InscripcionEntity> findByEventoIdEvento(Integer idEvento);

    // Para saber si un alumno ya está inscrito y no duplicarlo
    boolean existsByAlumnoIdAlumnoAndEventoIdEvento(Integer idAlumno, Integer idEvento);
}