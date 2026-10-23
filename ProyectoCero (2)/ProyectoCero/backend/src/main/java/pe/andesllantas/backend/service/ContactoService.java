package pe.andesllantas.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.andesllantas.backend.api.dto.ContactoRequest;
import pe.andesllantas.backend.api.dto.ContactoResponse;
import pe.andesllantas.backend.domain.SolicitudContacto;
import pe.andesllantas.backend.repository.SolicitudContactoRepository;

@Service
public class ContactoService {

  private final SolicitudContactoRepository solicitudContactoRepository;

  public ContactoService(SolicitudContactoRepository solicitudContactoRepository) {
    this.solicitudContactoRepository = solicitudContactoRepository;
  }

  @Transactional
  public ContactoResponse registrar(ContactoRequest request) {
    SolicitudContacto solicitud = new SolicitudContacto(
        request.nombre(),
        request.email(),
        request.telefono(),
        request.asunto(),
        request.mensaje()
    );

    SolicitudContacto guardada = solicitudContactoRepository.save(solicitud);
    return new ContactoResponse(guardada.getId(), guardada.getFechaCreacion(), "Solicitud registrada");
  }
}
