package pe.andesllantas.backend.api.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductoDetalleResponse(
    Long id,
    String slug,
    String nombre,
    String marca,
    String marcaLogoUrl,
    String categoria,
    String categoriaSlug,
    String subcategoria,
    BigDecimal precio,
    String descripcion,
    String medida,
    Integer ancho,
    Integer perfil,
    Integer aro,
    String indiceCarga,
    String indiceVelocidad,
    String tipo,
    String disponibilidad,
    String fichaTecnicaUrl,
    List<ImagenProductoResponse> imagenes,
    // PCR
    String treadwear,
    String traccion,
    String temperatura,
    // OTR / TBR
    String profCocada,
    String anchoBanda,
    String eje,
    String operacion,
    // Detalle
    Integer pliegues,
    String construccion,
    String disenio,
    String videoUrl
) {}