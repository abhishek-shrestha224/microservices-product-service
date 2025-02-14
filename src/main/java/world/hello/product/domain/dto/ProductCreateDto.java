package world.hello.product.domain.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ProductCreateDto(
    @NotBlank(message = "Product name must not be blank")
        @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
        String name,
    @NotBlank(message = "Category must not be blank")
        @Size(min = 3, max = 100, message = "Category must be between 3 and 100 characters")
        String category,
    @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be a positive number")
        @DecimalMin(value = "10", message = "Minimum price allowed is 10 rupees.")
        BigDecimal price) {}
