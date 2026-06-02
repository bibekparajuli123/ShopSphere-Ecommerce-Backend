package com.shopsphere.controller;

import com.shopsphere.dto.AddToCartRequest;
import com.shopsphere.dto.CartResponse;
import com.shopsphere.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse>
    addProductToCart(
            @Valid
            @RequestBody AddToCartRequest request) {

        return ResponseEntity.ok(
                cartService.addProductToCart(
                        request
                )
        );
    }
    @GetMapping("/{customerId}")
    public ResponseEntity<CartResponse>
    viewCart(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                cartService.viewCart(customerId)
        );
    }
    @DeleteMapping(
            "/{customerId}/product/{productId}"
    )
    public ResponseEntity<String>
    removeProductFromCart(
            @PathVariable Long customerId,
            @PathVariable Long productId) {

        cartService.removeProductFromCart(
                customerId,
                productId
        );

        return ResponseEntity.ok(
                "Product removed from cart"
        );
    }
}