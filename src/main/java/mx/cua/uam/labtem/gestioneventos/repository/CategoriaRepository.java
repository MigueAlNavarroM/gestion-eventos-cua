package mx.cua.uam.labtem.gestioneventos.repository;

import mx.cua.uam.labtem.gestioneventos.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Integer> {
    // Buscar categorías que generen certificado
    List<CategoriaEntity> findByGeneraCertificadoTrue();
}