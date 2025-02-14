package world.hello.product.domain.dto;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record Product(String id, String name, String category, BigDecimal price) {}
