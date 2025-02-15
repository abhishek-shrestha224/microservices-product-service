package world.hello.product.domain.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Builder;
import world.hello.product.domain.enums.Category;

@Builder
public record ProductData(
    @NotBlank(message = "Product name must not be blank")
        @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
        String name,
    Category category,
    @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be a positive number")
        @DecimalMin(value = "10", message = "Minimum price allowed is 10 rupees.")
        BigDecimal price) {}
