package pe.andesllantas.backend.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pe.andesllantas.backend.api.dto.ContactoRequest;
import pe.andesllantas.backend.api.dto.ContactoResponse;
import pe.andesllantas.backend.service.ContactoService;

@RestController
public class ContactoController {

  private final ContactoService contactoService;

  public ContactoController(ContactoService contactoService) {
    this.contactoService = contactoService;
  }

  @PostMapping("/contacto")
  @ResponseStatus(HttpStatus.CREATED)
  public ContactoResponse registrar(@Valid @RequestBody ContactoRequest request) {
    return contactoService.registrar(request);
  }
}
