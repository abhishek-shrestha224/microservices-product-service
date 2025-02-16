package world.hello.product.utils.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import world.hello.product.domain.dto.ProductUpdateData;
import world.hello.product.utils.ProductValidator;

@Component
@Slf4j
public class ProductValidatorImpl implements ProductValidator {
  @Override
  public boolean isMongoId(String id) {
    if (!id.matches("^[a-fA-F0-9]{24}$")) {
      log.warn("Invalid Id, does not match MonngoDB object id constraint");
      return false;
    }
    return true;
  }

  @Override
  public boolean hasAtLeastOneField(ProductUpdateData product) {
    if (product == null) {
      log.warn("Invalid Data, empty object");
      return false;
    }
    if (product.name().isEmpty() && product.category().isEmpty() && product.price().isEmpty()) {
      log.warn("Invalid Data, No field present");
      return false;
    }
    return true;
  }
}