package com.loja.ropa.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductCreateDTO(
        @NotBlank String name,
        @NotBlank String category,
        String size,
        @NotNull @DecimalMin("0.0") BigDecimal price,
        @NotNull @Min(0) Integer stock
) {}
