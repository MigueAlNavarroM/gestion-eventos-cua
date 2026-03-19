package mx.cua.uam.labtem.gestioneventos.repository;

import mx.cua.uam.labtem.gestioneventos.entity.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<InstructorEntity, Integer> {
    // Consulta personalizada para saltar de Instructor -> Evento -> Categoría
    @Query("SELECT DISTINCT i FROM InstructorEntity i JOIN i.eventos e WHERE e.categoria.idCategoria = :idCategoria")
    List<InstructorEntity> findByCategoriaId(@Param("idCategoria") Integer idCategoria);

    // Buscar instructores por especialidad
    List<InstructorEntity> findByEspecialidadContainingIgnoreCase(String especialidad);
}