package pe.andesllantas.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "imagenes_producto")
public class ImagenProducto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "producto_id", nullable = false)
  private Producto producto;

  @Column(name = "nombre_archivo", nullable = false, length = 180)
  private String nombreArchivo;

  @Column(nullable = false, length = 500)
  private String ruta;

  @Column(nullable = false, length = 500)
  private String url;

  @Column(nullable = false)
  private Integer orden;

  public Long getId() {
    return id;
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public String getRuta() {
    return ruta;
  }

  public String getUrl() {
    return url;
  }

  public Integer getOrden() {
    return orden;
  }
}
