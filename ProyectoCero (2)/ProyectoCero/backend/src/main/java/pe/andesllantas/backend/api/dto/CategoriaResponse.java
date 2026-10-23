package pe.andesllantas.backend.api.dto;

public record CategoriaResponse(
    Long id,
    String nombre,
    String slug
) {
}
