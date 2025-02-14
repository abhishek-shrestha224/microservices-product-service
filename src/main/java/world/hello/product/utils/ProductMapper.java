package world.hello.product.utils;

import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductCreateDto;
import world.hello.product.domain.model.ProductModel;

public interface ProductMapper {
  Product toDto(ProductModel model);

  ProductModel toModel(Product dto);

  ProductModel toModel(ProductCreateDto createDto);
}
