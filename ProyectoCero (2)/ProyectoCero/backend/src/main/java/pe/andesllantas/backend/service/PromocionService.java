package pe.andesllantas.backend.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.andesllantas.backend.api.dto.PromocionResponse;
import pe.andesllantas.backend.domain.EstadoPromocion;
import pe.andesllantas.backend.repository.PromocionRepository;

@Service
@Transactional(readOnly = true)
public class PromocionService {

  private final PromocionRepository promocionRepository;

  public PromocionService(PromocionRepository promocionRepository) {
    this.promocionRepository = promocionRepository;
  }

  public List<PromocionResponse> listarActivas() {
    LocalDate hoy = LocalDate.now();
    return promocionRepository
        .findByEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByFechaFinAsc(
            EstadoPromocion.ACTIVA,
            hoy,
            hoy
        )
        .stream()
        .map(promocion -> new PromocionResponse(
            promocion.getId(),
            promocion.getTitulo(),
            promocion.getImagenUrl(),
            promocion.getFechaInicio(),
            promocion.getFechaFin()
        ))
        .toList();
  }
}
