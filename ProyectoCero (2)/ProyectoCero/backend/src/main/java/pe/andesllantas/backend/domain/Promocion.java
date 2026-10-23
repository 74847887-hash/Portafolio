package pe.andesllantas.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "promociones")
public class Promocion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 160)
  private String titulo;

  @Column(name = "imagen_url", nullable = false, length = 500)
  private String imagenUrl;

  @Column(name = "fecha_inicio", nullable = false)
  private LocalDate fechaInicio;

  @Column(name = "fecha_fin", nullable = false)
  private LocalDate fechaFin;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EstadoPromocion estado;

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getImagenUrl() {
    return imagenUrl;
  }

  public LocalDate getFechaInicio() {
    return fechaInicio;
  }

  public LocalDate getFechaFin() {
    return fechaFin;
  }

  public EstadoPromocion getEstado() {
    return estado;
  }
}
