package world.hello.product.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductData;
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
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@RequestBody @Valid ProductData createDto) {
    log.info("POST /api/v1/products");
    log.info("Product create data: {}", createDto);
    if (createDto.name().equals("abcd")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are a teapot");
    }
    return productService.createProduct(createDto);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> getAllProducts() {
    log.info("GET /api/v1/products");
    return productService.fetchAllProducts();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product getProductById(@PathVariable("id") final String id) {
    log.info("GET /api/v1/products/{}", id);
    return productService.fetchProductById(id);
  }
}
