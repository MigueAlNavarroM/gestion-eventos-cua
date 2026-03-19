package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.dto.CertificadoDTO;
import mx.cua.uam.labtem.gestioneventos.entity.CertificadoEntity;
import mx.cua.uam.labtem.gestioneventos.entity.InscripcionEntity;
import mx.cua.uam.labtem.gestioneventos.repository.CertificadoRepository;
import mx.cua.uam.labtem.gestioneventos.repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CertificadoService {

    @Autowired
    private CertificadoRepository repo;

    @Autowired
    private InscripcionRepository inscripcionRepo;

    // Convierte la Entidad a DTO para que Postman vea nombres reales
    private CertificadoDTO convertirADTO(CertificadoEntity e) {
        return new CertificadoDTO(
                e.getFolio(),
                e.getFechaEmision(),
                e.getArchivo(),
                e.getInscripcion().getAlumno().getNombre(),
                e.getInscripcion().getEvento().getTitulo(),
                e.getInscripcion().getIdInscripcion()
        );
    }

    public List<CertificadoDTO> listar() {
        return repo.findAll().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public CertificadoDTO obtener(String folio) {
        return repo.findById(folio).map(this::convertirADTO).orElse(null);
    }

    // Lógica para POST (Generar desde inscripción)
    public CertificadoDTO crearDesdeInscripcion(Integer idInscripcion) {
        InscripcionEntity ins = inscripcionRepo.findById(idInscripcion).orElse(null);
        if (ins != null && ins.getEvento().getCategoria().getGeneraCertificado()) {
            CertificadoEntity nuevo = new CertificadoEntity();
            nuevo.setInscripcion(ins);
            nuevo.setFolio("UAM-CUA-" + ins.getAlumno().getMatricula() + "-" + ins.getEvento().getIdEvento());
            nuevo.setArchivo("constancia_" + ins.getAlumno().getMatricula() + ".pdf");
            return convertirADTO(repo.save(nuevo));
        }
        return null;
    }

    // Lógica para PUT (Actualización total)
    public CertificadoDTO actualizar(String folio, CertificadoEntity datosNuevos) {
        return repo.findById(folio).map(existente -> {
            existente.setArchivo(datosNuevos.getArchivo());
            // Aquí podrías actualizar más campos si fuera necesario
            return convertirADTO(repo.save(existente));
        }).orElse(null);
    }

    // Lógica para PATCH (Actualizar solo el nombre del archivo)
    public CertificadoDTO actualizarArchivo(String folio, String nuevoArchivo) {
        CertificadoEntity e = repo.findById(folio).orElse(null);
        if (e != null) {
            e.setArchivo(nuevoArchivo);
            return convertirADTO(repo.save(e));
        }
        return null;
    }

    public void eliminar(String folio) {
        repo.deleteById(folio);
    }
}