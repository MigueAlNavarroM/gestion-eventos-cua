package mx.cua.uam.labtem.gestioneventos.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "idEvento",
        "titulo",
        "descripcion",
        "fecha",
        "hora",
        "lugar",
        "categoria",
        "fechaCreacion" })
@Entity
@Table(name = "evento")
public class EventoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEvento;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private LocalDate fecha;
    private LocalTime hora;
    private String lugar;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;

    @ManyToMany
    @JoinTable(
            name = "evento_instructor",
            joinColumns = @JoinColumn(name = "id_evento"),
            inverseJoinColumns = @JoinColumn(name = "id_instructor")
    )
    private List<InstructorEntity> instructores = new ArrayList<>();

    @PrePersist
    protected void onCreate() { fechaCreacion = LocalDateTime.now(); }

    // Getters y Setters
    public Integer getIdEvento() { return idEvento; }
    public void setIdEvento(Integer idEvento) { this.idEvento = idEvento; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public LocalTime getHora() { return hora; }
    public void setHora(LocalTime hora) { this.hora = hora; }
    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public CategoriaEntity getCategoria() { return categoria; }
    public void setCategoria(CategoriaEntity categoria) { this.categoria = categoria; }
    public List<InstructorEntity> getInstructores() { return instructores; }
    public void setInstructores(List<InstructorEntity> instructores) { this.instructores = instructores; }
}