package pe.andesllantas.backend.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactoRequest(
    @NotBlank @Size(max = 120) String nombre,
    @NotBlank @Email @Size(max = 180) String email,
    @Size(max = 40) String telefono,
    @Size(max = 160) String asunto,
    @NotBlank @Size(max = 3000) String mensaje
) {
}
