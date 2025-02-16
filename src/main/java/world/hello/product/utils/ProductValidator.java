package world.hello.product.utils;

import world.hello.product.domain.dto.ProductUpdateData;

public interface ProductValidator {
  boolean isMongoId(String id);

  boolean hasAtLeastOneField(ProductUpdateData product);
}