package world.hello.product.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductData;
import world.hello.product.domain.dto.ProductUpdateData;
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

  @PatchMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Product updateProductById(
      @PathVariable("id") final String id, @RequestBody final ProductUpdateData updateData) {
    log.info("PATCH /api/v1/products/{}", id);
    log.info("Product update data: {}", updateData);
    return productService.updateProductById(id, updateData);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public String deleteProductById(@PathVariable("id") final String id) {
    log.info("DELETE /api/v1/products/{}", id);
    productService.deleteProdcutById(id);
    return "Product with id: " + id + "deleted successfully.";
  }
}