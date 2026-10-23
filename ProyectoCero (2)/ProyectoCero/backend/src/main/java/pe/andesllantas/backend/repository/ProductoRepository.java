package pe.andesllantas.backend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.andesllantas.backend.domain.EstadoProducto;
import pe.andesllantas.backend.domain.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

  @EntityGraph(attributePaths = {"marca", "categoria", "subcategoria", "imagenes"})
  List<Producto> findByEstadoOrderByMedidaAscNombreAsc(EstadoProducto estado);

  @EntityGraph(attributePaths = {"marca", "categoria", "subcategoria", "imagenes"})
  Optional<Producto> findBySlugAndEstado(String slug, EstadoProducto estado);

  @EntityGraph(attributePaths = {"marca", "categoria", "subcategoria", "imagenes"})
  List<Producto> findByEstadoAndNombreContainingIgnoreCaseOrderByMedidaAscNombreAsc(
      EstadoProducto estado,
      String nombre
  );

  @EntityGraph(attributePaths = {"marca", "categoria", "subcategoria", "imagenes"})
  List<Producto> findByEstadoAndMarcaNombreIgnoreCaseOrderByMedidaAscNombreAsc(
      EstadoProducto estado,
      String marca
  );

  @EntityGraph(attributePaths = {"marca", "categoria", "subcategoria", "imagenes"})
  List<Producto> findByEstadoAndCategoriaSlugOrderByMedidaAscNombreAsc(
      EstadoProducto estado,
      String categoriaSlug
  );

  @EntityGraph(attributePaths = {"marca", "categoria", "subcategoria", "imagenes"})
  List<Producto> findByEstadoAndMedidaContainingIgnoreCaseOrderByMedidaAscNombreAsc(
      EstadoProducto estado,
      String medida
  );
}
