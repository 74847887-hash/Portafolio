package pe.andesllantas.backend.api.dto;

import java.util.List;

public record FrontendProductResponse(
    Long id,
    String brand,
    String brandLogo,
    String brandLogoWidth,
    String image,
    String title,
    List<FrontendProductSpecResponse> specs,
    String price,
    String slug
) {
}
