package pe.andesllantas.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "marcas")
public class Marca {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 120)
  private String nombre;

  @Column(name = "logo_url", length = 500)
  private String logoUrl;

  public Long getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public String getLogoUrl() {
    return logoUrl;
  }
}
