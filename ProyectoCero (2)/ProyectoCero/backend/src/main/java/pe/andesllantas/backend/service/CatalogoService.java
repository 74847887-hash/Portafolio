package pe.andesllantas.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.andesllantas.backend.api.dto.CategoriaResponse;
import pe.andesllantas.backend.api.dto.MarcaResponse;
import pe.andesllantas.backend.repository.CategoriaRepository;
import pe.andesllantas.backend.repository.MarcaRepository;

@Service
@Transactional(readOnly = true)
public class CatalogoService {

  private final MarcaRepository marcaRepository;
  private final CategoriaRepository categoriaRepository;

  public CatalogoService(MarcaRepository marcaRepository, CategoriaRepository categoriaRepository) {
    this.marcaRepository = marcaRepository;
    this.categoriaRepository = categoriaRepository;
  }

  public List<MarcaResponse> listarMarcas() {
    return marcaRepository.findAllByOrderByNombreAsc().stream()
        .map(marca -> new MarcaResponse(marca.getId(), marca.getNombre(), marca.getLogoUrl()))
        .toList();
  }

  public List<CategoriaResponse> listarCategorias() {
    return categoriaRepository.findAllByOrderByNombreAsc().stream()
        .map(categoria -> new CategoriaResponse(categoria.getId(), categoria.getNombre(), categoria.getSlug()))
        .toList();
  }
}
