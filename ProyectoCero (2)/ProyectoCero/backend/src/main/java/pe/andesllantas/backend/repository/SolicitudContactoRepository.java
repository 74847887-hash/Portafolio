package pe.andesllantas.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.andesllantas.backend.domain.SolicitudContacto;

public interface SolicitudContactoRepository extends JpaRepository<SolicitudContacto, Long> {
}
