package world.hello.product.utils.impl;

import org.springframework.stereotype.Component;
import world.hello.product.domain.dto.Product;
import world.hello.product.domain.dto.ProductCreateDto;
import world.hello.product.domain.model.ProductModel;
import world.hello.product.utils.ProductMapper;

@Component
public class ProductMapperImpl implements ProductMapper {
  @Override
  public Product toDto(ProductModel model) {
    return Product.builder()
        .id(model.getId())
        .name(model.getName())
        .category(model.getCategory())
        .price(model.getPrice())
        .build();
  }

  @Override
  public ProductModel toModel(Product dto) {
    return ProductModel.builder()
        .id(dto.id())
        .name(dto.name())
        .category(dto.category())
        .price(dto.price())
        .build();
  }

  @Override
  public ProductModel toModel(ProductCreateDto createDto) {
    return ProductModel.builder()
        .id(null)
        .name(createDto.name())
        .category(createDto.category())
        .price(createDto.price())
        .build();
  }
}
