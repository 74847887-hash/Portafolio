package pe.andesllantas.backend.service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import pe.andesllantas.backend.api.dto.FrontendProductResponse;
import pe.andesllantas.backend.api.dto.FrontendProductSpecResponse;
import pe.andesllantas.backend.api.dto.ImagenProductoResponse;
import pe.andesllantas.backend.api.dto.ProductoDetalleResponse;
import pe.andesllantas.backend.api.dto.ProductoResumenResponse;
import pe.andesllantas.backend.domain.ImagenProducto;
import pe.andesllantas.backend.domain.Producto;

public final class ProductoMapper {

  private static final Locale PERU = Locale.forLanguageTag("es-PE");

  private ProductoMapper() {
  }

  public static ProductoResumenResponse toResumen(Producto producto) {
    return new ProductoResumenResponse(
        producto.getId(),
        producto.getSlug(),
        producto.getNombre(),
        producto.getMarca().getNombre(),
        producto.getMarca().getLogoUrl(),
        producto.getCategoria().getNombre(),
        producto.getCategoria().getSlug(),
        producto.getSubcategoria() == null ? null : producto.getSubcategoria().getNombre(),
        producto.getMedida(),
        producto.getPrecio(),
        producto.getDisponibilidad().name(),
        imagenPrincipal(producto),
        // PCR
        producto.getIndiceCarga(),
        producto.getIndiceVelocidad(),
        producto.getTreadwear(),
        producto.getTraccion(),
        producto.getTemperatura(),
        // OTR / TBR
        producto.getProfCocada(),
        producto.getAnchoBanda(),
        producto.getEje(),
        producto.getOperacion());
  }

  public static ProductoDetalleResponse toDetalle(Producto producto) {
    return new ProductoDetalleResponse(
        producto.getId(),
        producto.getSlug(),
        producto.getNombre(),
        producto.getMarca().getNombre(),
        producto.getMarca().getLogoUrl(),
        producto.getCategoria().getNombre(),
        producto.getCategoria().getSlug(),
        producto.getSubcategoria() == null ? null : producto.getSubcategoria().getNombre(),
        producto.getPrecio(),
        producto.getDescripcion(),
        producto.getMedida(),
        producto.getAncho(),
        producto.getPerfil(),
        producto.getAro(),
        producto.getIndiceCarga(),
        producto.getIndiceVelocidad(),
        producto.getTipo(),
        producto.getDisponibilidad().name(),
        producto.getFichaTecnicaUrl(),
        producto.getImagenes().stream().map(ProductoMapper::toImagen).toList(),
        // PCR
        producto.getTreadwear(),
        producto.getTraccion(),
        producto.getTemperatura(),
        // OTR / TBR
        producto.getProfCocada(),
        producto.getAnchoBanda(),
        producto.getEje(),
        producto.getOperacion(),
        // Detalle
        producto.getPliegues(),
        producto.getConstruccion(),
        producto.getDisenio(),
        producto.getVideoUrl());
  }

  public static FrontendProductResponse toFrontendProduct(Producto producto) {
    return new FrontendProductResponse(
        producto.getId(),
        producto.getMarca().getNombre(),
        producto.getMarca().getLogoUrl(),
        "110px",
        imagenPrincipal(producto),
        producto.getNombre(),
        List.of(
            new FrontendProductSpecResponse("fa-solid fa-circle-dot", producto.getMedida()),
            new FrontendProductSpecResponse("fa-solid fa-car", producto.getTipo()),
            new FrontendProductSpecResponse("fa-solid fa-gauge-high",
                "Indice de carga: " + nullSafe(producto.getIndiceCarga())),
            new FrontendProductSpecResponse("hero:shield-check",
                producto.getDisponibilidad().name().replace('_', ' '))),
        formatPrecio(producto),
        producto.getSlug());
  }

  private static ImagenProductoResponse toImagen(ImagenProducto imagen) {
    return new ImagenProductoResponse(
        imagen.getId(),
        imagen.getNombreArchivo(),
        imagen.getRuta(),
        imagen.getUrl(),
        imagen.getOrden());
  }

  private static String imagenPrincipal(Producto producto) {
    return producto.getImagenes().stream()
        .findFirst()
        .map(ImagenProducto::getUrl)
        .orElse(null);
  }

  private static String formatPrecio(Producto producto) {
    NumberFormat formatter = NumberFormat.getCurrencyInstance(PERU);
    return formatter.format(producto.getPrecio()).replace("PEN", "S/").trim();
  }

  private static String nullSafe(String value) {
    return value == null ? "N/D" : value;
  }
}