package pe.andesllantas.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "solicitudes_contacto")
public class SolicitudContacto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 120)
  private String nombre;

  @Column(nullable = false, length = 180)
  private String email;

  @Column(length = 40)
  private String telefono;

  @Column(length = 160)
  private String asunto;

  @Column(nullable = false, columnDefinition = "text")
  private String mensaje;

  @Column(name = "fecha_creacion", nullable = false)
  private Instant fechaCreacion;

  protected SolicitudContacto() {
  }

  public SolicitudContacto(String nombre, String email, String telefono, String asunto, String mensaje) {
    this.nombre = nombre;
    this.email = email;
    this.telefono = telefono;
    this.asunto = asunto;
    this.mensaje = mensaje;
    this.fechaCreacion = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public Instant getFechaCreacion() {
    return fechaCreacion;
  }
}
