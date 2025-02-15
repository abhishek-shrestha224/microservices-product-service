package world.hello.product.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
  @ResponseStatus(HttpStatus.CREATED)
  public Product createProduct(@RequestBody @Valid ProductCreateDto createDto) {
    log.info("Product create data: {}", createDto);
    if (createDto.name().equals("abcd")) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are a teapot");
    }
    final Product created = productService.createProduct(createDto);
    log.info("Created product: {}", created);
    return created;
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<Product> getAllProducts() {
    log.info("GET /api/v1/products");
    final List<Product> products = productService.fetchAllProducts();
    log.info("Found products: {}", products);
    return products;
  }
}
