package world.hello.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductData;
import world.hello.product.domain.model.ProductModel;
import world.hello.product.exception.GenericException;
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
  public Product createProduct(ProductData productCreateDto) {
    log.info("Creating product {}", productCreateDto);
    try {
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

  @Override
  public List<Product> fetchAllProducts() {
    log.info("Fetching all products");
    try {
      final List<ProductModel> products = productRepository.findAll();
      log.info("Found products: {}", products);
      if (products.size() < 1) {
        log.info("No products in database yet");
        return new ArrayList<Product>();
      }
      log.info("Attempting to map to dto");
      final List<Product> mappedProducts = products.stream().map(productMapper::toDto).toList();
      log.info("Mapped products: {}", mappedProducts);
      return mappedProducts;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch all product", ex);
    }
  }

  @Override
  public Product fetchProductById(String id) {
    try {
      log.info("Find product with id: {}", id);

      if (!id.matches("^[a-fA-F0-9]{24}$")) {
        log.warn("Invalid Id, doesnot match MonngoDB object id constraint");
        throw new GenericException(HttpStatus.BAD_REQUEST, "Invalid product ID format");
      }

      final ProductModel product =
          productRepository
              .findById(id)
              .orElseThrow(
                  () ->
                      new GenericException(
                          HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));

      log.info("Found Product: {}", product);
      return productMapper.toDto(product);
    } catch (GenericException ex) {
      log.error(ex.getMessage());
      throw new ResponseStatusException(ex.getExStatus(), ex.getMessage());
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch product by id: " + id);
    }
  }
}
