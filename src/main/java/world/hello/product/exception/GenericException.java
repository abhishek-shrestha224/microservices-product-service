package world.hello.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericException extends RuntimeException {

  private final HttpStatus exStatus;

  public GenericException(final HttpStatus exStatus, String message) {
    super(message);
    this.exStatus = exStatus;
  }

  public GenericException(final HttpStatus exStatus, String message, Throwable cause) {
    super(message, cause);
    this.exStatus = exStatus;
  }
}
