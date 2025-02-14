package world.hello.product.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductCreateDto;
import world.hello.product.domain.model.ProductModel;
import world.hello.product.repository.ProductRepository;
import world.hello.product.service.ProductService;
import world.hello.product.utils.ProductMapper;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductServiceImpl(
      final ProductRepository productRepository, final ProductMapper productMapper) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
  }

  @Override
  public Product createProduct(ProductCreateDto productCreateDto) {
    try {
      log.info("Creating product {}", productCreateDto);
      final ProductModel product = productMapper.toModel(productCreateDto);
      log.info("Mapped to Model {}", product);
      final ProductModel created = productRepository.save(product);
      log.info("Created Model {}", created);
      return productMapper.toDto(created);
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create product", ex);
    }
  }
}
