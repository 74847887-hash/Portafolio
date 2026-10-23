package pe.andesllantas.backend.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 180)
  private String slug;

  @Column(nullable = false, length = 180)
  private String nombre;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "marca_id", nullable = false)
  private Marca marca;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "categoria_id", nullable = false)
  private Categoria categoria;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subcategoria_id")
  private Subcategoria subcategoria;

  @Column(nullable = false, precision = 12, scale = 2)
  private BigDecimal precio;

  @Column(columnDefinition = "text")
  private String descripcion;

  @Column(nullable = false, length = 60)
  private String medida;

  private Integer ancho;

  private Integer perfil;

  private Integer aro;

  @Column(name = "indice_carga", length = 40)
  private String indiceCarga;

  @Column(name = "indice_velocidad", length = 20)
  private String indiceVelocidad;

  // PCR
  @Column(length = 20)
  private String treadwear;

  @Column(length = 10)
  private String traccion;

  @Column(length = 10)
  private String temperatura;

  // OTR / TBR
  @Column(name = "prof_cocada", length = 20)
  private String profCocada;

  @Column(name = "ancho_banda", length = 20)
  private String anchoBanda;

  @Column(length = 40)
  private String eje;

  @Column(length = 40)
  private String operacion;

  @Column
  private Integer pliegues;

  @Column(length = 40)
  private String construccion;

  @Column(length = 40)
  private String disenio;

  @Column(name = "video_url", length = 500)
  private String videoUrl;

  @Column(length = 80)
  private String tipo;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private Disponibilidad disponibilidad;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EstadoProducto estado;

  @Column(name = "ficha_tecnica_url", length = 500)
  private String fichaTecnicaUrl;

  @Column(name = "fecha_creacion", nullable = false)
  private Instant fechaCreacion;

  @Column(name = "fecha_actualizacion", nullable = false)
  private Instant fechaActualizacion;

  @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ImagenProducto> imagenes = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public String getSlug() {
    return slug;
  }

  public String getNombre() {
    return nombre;
  }

  public Marca getMarca() {
    return marca;
  }

  public Categoria getCategoria() {
    return categoria;
  }

  public Subcategoria getSubcategoria() {
    return subcategoria;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getMedida() {
    return medida;
  }

  public Integer getAncho() {
    return ancho;
  }

  public Integer getPerfil() {
    return perfil;
  }

  public Integer getAro() {
    return aro;
  }

  public String getIndiceCarga() {
    return indiceCarga;
  }

  public String getIndiceVelocidad() {
    return indiceVelocidad;
  }

  public String getTreadwear() {
    return treadwear;
  }

  public String getTraccion() {
    return traccion;
  }

  public String getTemperatura() {
    return temperatura;
  }

  public String getProfCocada() {
    return profCocada;
  }

  public String getAnchoBanda() {
    return anchoBanda;
  }

  public String getEje() {
    return eje;
  }

  public String getOperacion() {
    return operacion;
  }

  public String getTipo() {
    return tipo;
  }

  public Disponibilidad getDisponibilidad() {
    return disponibilidad;
  }

  public EstadoProducto getEstado() {
    return estado;
  }

  public String getFichaTecnicaUrl() {
    return fichaTecnicaUrl;
  }

  public Instant getFechaCreacion() {
    return fechaCreacion;
  }

  public Instant getFechaActualizacion() {
    return fechaActualizacion;
  }

  public List<ImagenProducto> getImagenes() {
    return imagenes.stream()
        .sorted(Comparator.comparing(ImagenProducto::getOrden))
        .toList();
  }

  public Integer getPliegues() {
    return pliegues;
  }

  public String getConstruccion() {
    return construccion;
  }

  public String getDisenio() {
    return disenio;
  }

  public String getVideoUrl() {
    return videoUrl;
  }
}