package pe.andesllantas.backend.api;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pe.andesllantas.backend.service.RecursoNoEncontradoException;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(RecursoNoEncontradoException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error(HttpStatus.NOT_FOUND, exception.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException exception) {
    String message = exception.getBindingResult().getFieldErrors().stream()
        .findFirst()
        .map(error -> error.getField() + ": " + error.getDefaultMessage())
        .orElse("Solicitud invalida");

    return ResponseEntity.badRequest().body(error(HttpStatus.BAD_REQUEST, message));
  }

  private Map<String, Object> error(HttpStatus status, String message) {
    return Map.of(
        "timestamp", Instant.now(),
        "status", status.value(),
        "error", status.getReasonPhrase(),
        "message", message
    );
  }
}
