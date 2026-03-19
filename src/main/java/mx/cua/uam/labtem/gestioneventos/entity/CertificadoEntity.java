package mx.cua.uam.labtem.gestioneventos.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@JsonPropertyOrder({
        "folio",
        "fechaEmision",
        "archivo",
        "inscripcion"
}) // Ordenamos para que el folio sea lo primero
@Entity
@Table(name = "certificado")
public class CertificadoEntity {

    @Id
    @Column(length = 40)
    private String folio;

    @Column(name = "fecha_emision", updatable = false)
    private LocalDateTime fechaEmision;

    private String archivo;

    @OneToOne
    @JoinColumn(name = "id_inscripcion", unique = true)
    private InscripcionEntity inscripcion;

    @PrePersist
    protected void onCreate() {
        fechaEmision = LocalDateTime.now();
    }

    // --- Getters y Setters ---
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
    public String getArchivo() { return archivo; }
    public void setArchivo(String archivo) { this.archivo = archivo; }
    public InscripcionEntity getInscripcion() { return inscripcion; }
    public void setInscripcion(InscripcionEntity inscripcion) { this.inscripcion = inscripcion; }
}