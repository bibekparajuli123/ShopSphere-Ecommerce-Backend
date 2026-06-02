package com.shopsphere.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long cartId;

    private Long customerId;

    private List<CartItemResponse> items;
}