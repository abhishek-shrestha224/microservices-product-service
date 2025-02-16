package world.hello.product.service;

import java.util.List;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductData;
import world.hello.product.domain.dto.ProductUpdateData;

public interface ProductService {
  Product createProduct(ProductData productCreateDto);

  List<Product> fetchAllProducts();

  Product fetchProductById(String id);

  Product updateProductById(String id, ProductUpdateData productUpdate);
}