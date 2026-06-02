package com.shopsphere.service.impl;
import com.shopsphere.dto.*;
import com.shopsphere.entity.*;
import com.shopsphere.exception.ResourceNotFoundException;

import java.util.List;
import com.shopsphere.dto.AddToCartRequest;
import com.shopsphere.dto.CartResponse;
import com.shopsphere.repository.CartItemRepository;
import com.shopsphere.repository.CartRepository;
import com.shopsphere.repository.CustomerRepository;
import com.shopsphere.repository.ProductRepository;
import com.shopsphere.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl
        implements CartService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final CustomerRepository customerRepository;

    private final ProductRepository productRepository;

    @Override
    public CartResponse addProductToCart(
            AddToCartRequest request) {

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + request.getCustomerId()
                        ));

        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + request.getProductId()
                        ));

        Cart cart = cartRepository
                .findByCustomerCustomerId(
                        customer.getCustomerId()
                )
                .orElseGet(() -> {

                    Cart newCart = Cart.builder()
                            .customer(customer)
                            .build();

                    return cartRepository.save(newCart);
                });

        CartItem cartItem = cartItemRepository
                .findByCartCartIdAndProductProductId(
                        cart.getCartId(),
                        product.getProductId()
                )
                .orElse(null);

        if (cartItem != null) {

            cartItem.setQuantity(
                    cartItem.getQuantity()
                            + request.getQuantity()
            );

        } else {

            cartItem = CartItem.builder()
                    .cart(cart)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
        }

        cartItemRepository.save(cartItem);

        return buildCartResponse(cart);
    }

    @Override
    public CartResponse viewCart(
            Long customerId) {

        Cart cart = cartRepository
                .findByCustomerCustomerId(
                        customerId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found for customer id: "
                                        + customerId
                        ));

        return buildCartResponse(cart);
    }

    @Override
    public void removeProductFromCart(
            Long customerId,
            Long productId) {

        Cart cart = cartRepository
                .findByCustomerCustomerId(
                        customerId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found for customer id: "
                                        + customerId
                        ));

        CartItem cartItem =
                cartItemRepository
                        .findByCartCartIdAndProductProductId(
                                cart.getCartId(),
                                productId
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found in cart"
                                ));

        cartItemRepository.delete(cartItem);
    }

    private CartResponse buildCartResponse(
            Cart cart) {

        List<CartItemResponse> items =
                cartItemRepository
                        .findByCartCartId(
                                cart.getCartId()
                        )
                        .stream()
                        .map(item ->
                                CartItemResponse.builder()
                                        .productId(
                                                item.getProduct()
                                                        .getProductId()
                                        )
                                        .productName(
                                                item.getProduct()
                                                        .getName()
                                        )
                                        .price(
                                                item.getProduct()
                                                        .getPrice()
                                        )
                                        .quantity(
                                                item.getQuantity()
                                        )
                                        .build()
                        )
                        .toList();

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .customerId(
                        cart.getCustomer()
                                .getCustomerId()
                )
                .items(items)
                .build();
    }
}