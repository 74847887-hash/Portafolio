package pe.andesllantas.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import pe.andesllantas.backend.domain.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Long> {

  List<Marca> findAllByOrderByNombreAsc();
}
