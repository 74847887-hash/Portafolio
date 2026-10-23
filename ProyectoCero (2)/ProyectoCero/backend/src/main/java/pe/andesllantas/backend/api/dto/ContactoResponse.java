package pe.andesllantas.backend.api.dto;

import java.time.Instant;

public record ContactoResponse(
    Long id,
    Instant fechaCreacion,
    String mensaje
) {
}
