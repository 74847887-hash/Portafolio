package pe.andesllantas.backend.api;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.andesllantas.backend.api.dto.PromocionResponse;
import pe.andesllantas.backend.service.PromocionService;

@RestController
public class PromocionController {

  private final PromocionService promocionService;

  public PromocionController(PromocionService promocionService) {
    this.promocionService = promocionService;
  }

  @GetMapping("/promociones/activas")
  public List<PromocionResponse> listarActivas() {
    return promocionService.listarActivas();
  }
}
