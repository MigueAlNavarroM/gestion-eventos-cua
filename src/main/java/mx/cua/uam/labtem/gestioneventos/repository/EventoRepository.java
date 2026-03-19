package mx.cua.uam.labtem.gestioneventos.repository;

import mx.cua.uam.labtem.gestioneventos.entity.EventoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<EventoEntity, Integer> {
    // Filtrar eventos por categoría
    List<EventoEntity> findByCategoriaIdCategoria(Integer idCategoria);

    // Buscar por título
    List<EventoEntity> findByTituloContainingIgnoreCase(String titulo);

    // Buscar eventos en un lugar específico
    List<EventoEntity> findByLugarContainingIgnoreCase(String lugar);
}