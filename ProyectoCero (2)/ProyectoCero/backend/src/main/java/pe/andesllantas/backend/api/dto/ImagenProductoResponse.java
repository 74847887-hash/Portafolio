package pe.andesllantas.backend.api.dto;

public record ImagenProductoResponse(
    Long id,
    String nombreArchivo,
    String ruta,
    String url,
    Integer orden
) {
}
