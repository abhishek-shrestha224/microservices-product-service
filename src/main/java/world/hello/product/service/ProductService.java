package world.hello.product.service;

import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductCreateDto;

public interface ProductService {
  Product createProduct(ProductCreateDto productCreateDto);
}
