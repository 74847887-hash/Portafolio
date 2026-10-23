package pe.andesllantas.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.andesllantas.backend.api.dto.FrontendProductResponse;
import pe.andesllantas.backend.api.dto.ProductoDetalleResponse;
import pe.andesllantas.backend.api.dto.ProductoResumenResponse;
import pe.andesllantas.backend.domain.EstadoProducto;
import pe.andesllantas.backend.repository.ProductoRepository;

@Service
@Transactional(readOnly = true)
public class ProductoService {

  private final ProductoRepository productoRepository;

  public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
  }

  public List<ProductoResumenResponse> listar(String marca, String categoria, String medida) {
    if (marca != null && !marca.isBlank()) {
      return productoRepository
          .findByEstadoAndMarcaNombreIgnoreCaseOrderByMedidaAscNombreAsc(EstadoProducto.ACTIVO, marca)
          .stream()
          .map(ProductoMapper::toResumen)
          .toList();
    }

    if (categoria != null && !categoria.isBlank()) {
      return productoRepository
          .findByEstadoAndCategoriaSlugOrderByMedidaAscNombreAsc(EstadoProducto.ACTIVO, categoria)
          .stream()
          .map(ProductoMapper::toResumen)
          .toList();
    }

    if (medida != null && !medida.isBlank()) {
      return productoRepository
          .findByEstadoAndMedidaContainingIgnoreCaseOrderByMedidaAscNombreAsc(EstadoProducto.ACTIVO, medida)
          .stream()
          .map(ProductoMapper::toResumen)
          .toList();
    }

    return productoRepository.findByEstadoOrderByMedidaAscNombreAsc(EstadoProducto.ACTIVO)
        .stream()
        .map(ProductoMapper::toResumen)
        .toList();
  }

  public ProductoDetalleResponse obtenerPorSlug(String slug) {
    return productoRepository.findBySlugAndEstado(slug, EstadoProducto.ACTIVO)
        .map(ProductoMapper::toDetalle)
        .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
  }

  public List<ProductoResumenResponse> buscar(String query) {
    if (query == null || query.isBlank()) {
      return listar(null, null, null);
    }

    return productoRepository
        .findByEstadoAndNombreContainingIgnoreCaseOrderByMedidaAscNombreAsc(EstadoProducto.ACTIVO, query)
        .stream()
        .map(ProductoMapper::toResumen)
        .toList();
  }

  public List<FrontendProductResponse> listarParaFrontendActual() {
    return productoRepository.findByEstadoOrderByMedidaAscNombreAsc(EstadoProducto.ACTIVO)
        .stream()
        .map(ProductoMapper::toFrontendProduct)
        .toList();
  }

  public List<ProductoResumenResponse> listarRelacionados(String slug) {
    return productoRepository.findBySlugAndEstado(slug, EstadoProducto.ACTIVO)
        .map(producto -> productoRepository
            .findByEstadoAndCategoriaSlugOrderByMedidaAscNombreAsc(
                EstadoProducto.ACTIVO,
                producto.getCategoria().getSlug())
            .stream()
            .filter(p -> !p.getSlug().equals(slug))
            .limit(4)
            .map(ProductoMapper::toResumen)
            .toList())
        .orElse(List.of());
  }
}
