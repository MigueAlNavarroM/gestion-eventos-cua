package mx.cua.uam.labtem.gestioneventos.service;

import mx.cua.uam.labtem.gestioneventos.model.Instructor;
import mx.cua.uam.labtem.gestioneventos.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    public List<Instructor> obtenerTodos() {
        return instructorRepository.findAll();
    }

    public Optional<Instructor> obtenerPorId(Integer id) {
        return instructorRepository.findById(id);
    }

    public Instructor guardar(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public Instructor actualizar(Integer id, Instructor instructorActualizado) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + id));

        instructor.setNombre(instructorActualizado.getNombre());
        instructor.setApellidoPaterno(instructorActualizado.getApellidoPaterno());
        instructor.setApellidoMaterno(instructorActualizado.getApellidoMaterno());
        instructor.setCorreo(instructorActualizado.getCorreo());
        instructor.setTelefono(instructorActualizado.getTelefono());
        instructor.setEspecialidad(instructorActualizado.getEspecialidad());
        instructor.setBio(instructorActualizado.getBio());
        instructor.setActivo(instructorActualizado.getActivo());

        return instructorRepository.save(instructor);
    }

    public void eliminar(Integer id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado con id: " + id));

        instructorRepository.delete(instructor);
    }
}
