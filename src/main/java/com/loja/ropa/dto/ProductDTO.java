package com.loja.ropa.dto;

import java.math.BigDecimal;

public record ProductDTO(
        Long id,
        String name,
        String category,
        String size,
        BigDecimal price,
        Integer stock
) {}