package world.hello.product.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EntryController {
  @GetMapping
  public String index() {
    log.info("Test Endpoint");
    return "Hello World";
  }

  @GetMapping("/abcd")
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Object> getProducts() {
    log.info("Test abcd");
    Map<String, Object> response = new HashMap<>();
    response.put("id", 1);
    response.put("name", "Product A");
    response.put("price", 29.99);
    return response;
  }
}
