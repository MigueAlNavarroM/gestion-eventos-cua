package mx.cua.uam.labtem.gestioneventos.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@JsonPropertyOrder({
        "idInscripcion",
        "evento",
        "alumno",
        "inscripto",
        "fechaRegistro"
})
@Entity
@Table(name = "inscripcion")
public class InscripcionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInscripcion;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private EventoEntity evento;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private AlumnoEntity alumno;

    @Column(nullable = false)
    private Boolean inscripto = true;

    @Column(name = "fecha_registro", updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    protected void onCreate() { fechaRegistro = LocalDateTime.now(); }

    // Getters y Setters
    public Integer getIdInscripcion() { return idInscripcion; }
    public void setIdInscripcion(Integer idInscripcion) { this.idInscripcion = idInscripcion; }
    public EventoEntity getEvento() { return evento; }
    public void setEvento(EventoEntity evento) { this.evento = evento; }
    public AlumnoEntity getAlumno() { return alumno; }
    public void setAlumno(AlumnoEntity alumno) { this.alumno = alumno; }
    public Boolean getInscripto() { return inscripto; }
    public void setInscripto(Boolean inscripto) { this.inscripto = inscripto; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}