package world.hello.product.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class AppExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleValidationRulesFailure(MethodArgumentNotValidException ex) {
    Map<String, String> errMap = new HashMap<>();
    errMap.put("title", "Validation Failed");
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(err -> errMap.put(err.getField(), err.getDefaultMessage()));
    return errMap;
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Map<String, String> handleArgumentMismatchException(
      MethodArgumentTypeMismatchException ex) {
    final Map<String, String> response = new HashMap<>();
    response.put("message", ex.getMessage());
    response.put(
        "details",
        "Argument type mismatch: " + ex.getName() + " expected type: " + ex.getRequiredType());
    return response;
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<String> genericExceptionHandler(ResponseStatusException ex) {
    return ResponseEntity.status(ex.getStatusCode().value()).body(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<Map<String, String>> fallbackExceptionHandler(Exception ex) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("message", ex.getMessage());
    errorResponse.put("type", ex.getClass().getSimpleName());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
