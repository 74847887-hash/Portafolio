package pe.andesllantas.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.andesllantas.backend.domain.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

  @EntityGraph(attributePaths = {})
  List<Categoria> findAllByOrderByNombreAsc();
}
