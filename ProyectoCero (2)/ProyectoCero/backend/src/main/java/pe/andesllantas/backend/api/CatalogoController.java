package pe.andesllantas.backend.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.andesllantas.backend.api.dto.CategoriaResponse;
import pe.andesllantas.backend.api.dto.MarcaResponse;
import pe.andesllantas.backend.service.CatalogoService;

@RestController
public class CatalogoController {

  private final CatalogoService catalogoService;

  public CatalogoController(CatalogoService catalogoService) {
    this.catalogoService = catalogoService;
  }

  @GetMapping("/marcas")
  public List<MarcaResponse> listarMarcas() {
    return catalogoService.listarMarcas();
  }

  @GetMapping("/categorias")
  public List<CategoriaResponse> listarCategorias() {
    return catalogoService.listarCategorias();
  }
}
