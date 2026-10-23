package pe.andesllantas.backend.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.andesllantas.backend.api.dto.FrontendProductResponse;
import pe.andesllantas.backend.service.ProductoService;

@RestController
public class FrontendCompatibilityController {

  private final ProductoService productoService;

  public FrontendCompatibilityController(ProductoService productoService) {
    this.productoService = productoService;
  }

  @GetMapping("/products")
  public List<FrontendProductResponse> listarParaFrontendActual() {
    return productoService.listarParaFrontendActual();
  }
}
