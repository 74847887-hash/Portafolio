package pe.andesllantas.backend.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.andesllantas.backend.api.dto.ProductoDetalleResponse;
import pe.andesllantas.backend.api.dto.ProductoResumenResponse;
import pe.andesllantas.backend.service.ProductoService;

@RestController
public class ProductoController {

  private final ProductoService productoService;

  public ProductoController(ProductoService productoService) {
    this.productoService = productoService;
  }

  @GetMapping("/productos")
  public List<ProductoResumenResponse> listar(
      @RequestParam(required = false) String marca,
      @RequestParam(required = false) String categoria,
      @RequestParam(required = false) String medida) {
    return productoService.listar(marca, categoria, medida);
  }

  @GetMapping("/productos/buscar")
  public List<ProductoResumenResponse> buscar(@RequestParam(name = "q", required = false) String query) {
    return productoService.buscar(query);
  }

  @GetMapping("/productos/{slug}")
  public ProductoDetalleResponse obtenerPorSlug(@PathVariable String slug) {
    return productoService.obtenerPorSlug(slug);
  }

  @GetMapping("/productos/{slug}/relacionados")
  public List<ProductoResumenResponse> relacionados(@PathVariable String slug) {
    return productoService.listarRelacionados(slug);
  }
}
