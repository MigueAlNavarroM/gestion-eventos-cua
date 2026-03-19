package mx.cua.uam.labtem.gestioneventos.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.util.List;

@JsonPropertyOrder({
        "idAlumno",
        "nombre",
        "matricula",
        "correo",
        "passwordHash"
})
@Entity
@Table(name = "alumno")
public class AlumnoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlumno;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(nullable = false, unique = true, length = 120)
    private String correo;

    // Agregué WRITE_ONLY para que la contraseña se pueda guardar pero no se vea en los GET
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @OneToMany(mappedBy = "alumno")
    private List<InscripcionEntity> inscripciones;

    // --- Getters y Setters ---
    public Integer getIdAlumno() { return idAlumno; }
    public void setIdAlumno(Integer idAlumno) { this.idAlumno = idAlumno; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
}