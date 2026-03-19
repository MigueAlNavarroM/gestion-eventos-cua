package mx.cua.uam.labtem.gestioneventos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "idInstructor",
        "nombre",
        "apellidoPaterno",
        "apellidoMaterno",
        "correo",
        "telefono",
        "especialidad",
        "bio",
        "activo"
})
@Entity
@Table(name = "instructor")
public class InstructorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInstructor;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private String especialidad;
    private String bio;
    private Boolean activo;

    @ManyToMany(mappedBy = "instructores")
    @JsonIgnore // Mantenemos el ignore para el JSON general, pero el service lo usará manualmente
    private List<EventoEntity> eventos = new ArrayList<>();

    // Getters y Setters
    public Integer getIdInstructor() { return idInstructor; }
    public void setIdInstructor(Integer idInstructor) { this.idInstructor = idInstructor; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    public List<EventoEntity> getEventos() { return eventos; }
    public void setEventos(List<EventoEntity> eventos) { this.eventos = eventos; }
}