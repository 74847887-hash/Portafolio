package pe.andesllantas.backend.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.andesllantas.backend.domain.EstadoPromocion;
import pe.andesllantas.backend.domain.Promocion;

public interface PromocionRepository extends JpaRepository<Promocion, Long> {

  List<Promocion> findByEstadoAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqualOrderByFechaFinAsc(
      EstadoPromocion estado,
      LocalDate fechaInicio,
      LocalDate fechaFin
  );
}
