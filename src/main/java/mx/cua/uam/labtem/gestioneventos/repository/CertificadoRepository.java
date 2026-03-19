package mx.cua.uam.labtem.gestioneventos.repository;

import mx.cua.uam.labtem.gestioneventos.entity.CertificadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CertificadoRepository extends JpaRepository<CertificadoEntity, String> {
    // Buscar certificado por el ID de la inscripción
    Optional<CertificadoEntity> findByInscripcionIdInscripcion(Integer idInscripcion);
}