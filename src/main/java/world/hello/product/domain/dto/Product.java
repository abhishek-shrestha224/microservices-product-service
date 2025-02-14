package world.hello.product.domain.dto;

import java.math.BigDecimal;
import lombok.Builder;
import world.hello.product.domain.enums.Category;

@Builder
public record Product(String id, String name, Category category, BigDecimal price) {}
