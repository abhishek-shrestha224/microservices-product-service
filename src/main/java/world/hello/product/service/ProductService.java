package world.hello.product.service;

import java.util.List;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductCreateDto;

public interface ProductService {
  Product createProduct(ProductCreateDto productCreateDto);

  List<Product> fetchAllProducts();

  Product fetchProductById(String id);
}
