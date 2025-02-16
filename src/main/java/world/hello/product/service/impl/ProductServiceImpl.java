package world.hello.product.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductData;
import world.hello.product.domain.dto.ProductUpdateData;
import world.hello.product.domain.model.ProductModel;
import world.hello.product.exception.GenericException;
import world.hello.product.repository.ProductRepository;
import world.hello.product.service.ProductService;
import world.hello.product.utils.ProductMapper;
import world.hello.product.utils.ProductValidator;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;
  private final ProductValidator productValidator;

  public ProductServiceImpl(
      final ProductRepository productRepository,
      final ProductMapper productMapper,
      final ProductValidator productValidator) {
    this.productRepository = productRepository;
    this.productMapper = productMapper;
    this.productValidator = productValidator;
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
      if (products.isEmpty()) {
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

      if (!productValidator.isMongoId(id)) {
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

  @Override
  public Product updateProductById(String id, ProductUpdateData productUpdate) {
    try {
      log.info("Updating product with id: {}", id);
      if (!productValidator.isMongoId(id)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product ID format");
      }

      if (!productValidator.hasAtLeastOneField(productUpdate)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must have at least one field.");
      }
      ProductModel existingProduct =
          productRepository
              .findById(id)
              .orElseThrow(
                  () ->
                      new ResponseStatusException(
                          HttpStatus.NOT_FOUND, "Product with id " + id + " not found"));
      log.info("Product found: {}", existingProduct);

      existingProduct.setName(productUpdate.name().orElse(existingProduct.getName()));
      existingProduct.setCategory(productUpdate.category().orElse(existingProduct.getCategory()));
      existingProduct.setPrice(productUpdate.price().orElse(existingProduct.getPrice()));

      log.info("Updated product: {}", existingProduct);

      return productMapper.toDto(productRepository.save(existingProduct));
    } catch (ResponseStatusException ex) {
      log.error(ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update product by id: " + id);
    }
  }

  @Override
  public void deleteProdcutById(String id) {
    try {
      log.info("Deleting product with id: {}", id);
      if (!productValidator.isMongoId(id)) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid product ID format");
      }
      productRepository.deleteById(id);
    } catch (ResponseStatusException ex) {
      log.error(ex.getMessage());
      throw ex;
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete product by id: " + id);
    }
  }
}