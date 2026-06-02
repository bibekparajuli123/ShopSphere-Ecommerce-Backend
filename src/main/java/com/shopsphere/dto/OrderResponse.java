package com.shopsphere.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long orderId;

    private Long customerId;

    private LocalDateTime orderDate;

    private BigDecimal totalAmount;

    private List<OrderItemResponse> items;
}