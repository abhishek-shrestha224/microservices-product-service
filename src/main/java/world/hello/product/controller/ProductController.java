package world.hello.product.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductCreateDto;
import world.hello.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {
  private final ProductService productService;

  public ProductController(final ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductCreateDto createDto) {
    log.info("Product create data: {}", createDto);
    if (createDto.name().equals("abcd")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are a teapot");
    }
    final Product created = productService.createProduct(createDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
  }
}
