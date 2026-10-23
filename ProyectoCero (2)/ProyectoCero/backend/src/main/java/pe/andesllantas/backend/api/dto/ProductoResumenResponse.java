package pe.andesllantas.backend.api.dto;

import java.math.BigDecimal;

public record ProductoResumenResponse(
    Long id,
    String slug,
    String nombre,
    String marca,
    String marcaLogoUrl,
    String categoria,
    String categoriaSlug,
    String subcategoria,
    String medida,
    BigDecimal precio,
    String disponibilidad,
    String imagenPrincipalUrl,
    // PCR
    String indiceCarga,
    String indiceVelocidad,
    String treadwear,
    String traccion,
    String temperatura,
    // OTR / TBR
    String profCocada,
    String anchoBanda,
    String eje,
    String operacion
) {}