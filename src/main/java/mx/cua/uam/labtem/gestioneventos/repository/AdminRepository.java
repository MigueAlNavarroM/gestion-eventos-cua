package mx.cua.uam.labtem.gestioneventos.repository;

import mx.cua.uam.labtem.gestioneventos.entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
    Optional<AdminEntity> findByCorreo(String correo);
}