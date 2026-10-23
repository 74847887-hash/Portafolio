package pe.andesllantas.backend.api.dto;

import java.time.LocalDate;

public record PromocionResponse(
    Long id,
    String titulo,
    String imagenUrl,
    LocalDate fechaInicio,
    LocalDate fechaFin
) {
}
