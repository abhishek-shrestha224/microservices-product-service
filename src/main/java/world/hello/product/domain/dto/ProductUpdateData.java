package world.hello.product.domain.dto;

import lombok.Builder;
import world.hello.product.domain.enums.Category;

import java.math.BigDecimal;
import java.util.Optional;

@Builder
public record ProductUpdateData(
    Optional<String> name, Optional<Category> category, Optional<BigDecimal> price) {}