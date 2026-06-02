package com.shopsphere.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private Long categoryId;

    private String categoryName;
}