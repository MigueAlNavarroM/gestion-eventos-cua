package mx.cua.uam.labtem.gestioneventos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "idCategoria",
        "nombre",
        "descripcion",
        "generaCertificado" })
@Entity
@Table(name = "categoria")
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    private String nombre;
    private String descripcion;
    private Boolean generaCertificado;

    @OneToMany(mappedBy = "categoria")
    @JsonIgnore
    private List<EventoEntity> eventos = new ArrayList<>();

    // Getters y Setters
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getGeneraCertificado() { return generaCertificado; }
    public void setGeneraCertificado(Boolean generaCertificado) { this.generaCertificado = generaCertificado; }
    public List<EventoEntity> getEventos() { return eventos; }
    public void setEventos(List<EventoEntity> eventos) { this.eventos = eventos; }
}