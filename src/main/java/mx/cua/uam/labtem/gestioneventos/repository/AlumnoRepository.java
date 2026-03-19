package mx.cua.uam.labtem.gestioneventos.repository;

import mx.cua.uam.labtem.gestioneventos.entity.AlumnoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<AlumnoEntity, Integer> {
    Optional<AlumnoEntity> findByCorreo(String correo);
    Optional<AlumnoEntity> findByMatricula(String matricula);
}
