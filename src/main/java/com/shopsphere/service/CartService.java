package com.shopsphere.service;

import com.shopsphere.dto.AddToCartRequest;
import com.shopsphere.dto.CartResponse;

public interface CartService {

    CartResponse addProductToCart(
            AddToCartRequest request);

    CartResponse viewCart(
            Long customerId);

    void removeProductFromCart(
            Long customerId,
            Long productId);
}