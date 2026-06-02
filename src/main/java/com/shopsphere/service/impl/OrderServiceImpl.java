package com.shopsphere.service.impl;

import com.shopsphere.dto.OrderResponse;
import com.shopsphere.repository.CartItemRepository;
import com.shopsphere.repository.CartRepository;
import com.shopsphere.repository.CustomerRepository;
import com.shopsphere.repository.OrderItemRepository;
import com.shopsphere.repository.OrderRepository;
import com.shopsphere.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.shopsphere.dto.OrderItemResponse;
import com.shopsphere.dto.OrderResponse;
import com.shopsphere.entity.*;
import com.shopsphere.exception.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final CustomerRepository customerRepository;

    @Override
    public OrderResponse placeOrder(Long customerId) {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + customerId
                        ));

        Cart cart = cartRepository
                .findByCustomerCustomerId(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Cart not found"
                        ));

        List<CartItem> cartItems =
                cartItemRepository.findByCartCartId(
                        cart.getCartId()
                );

        if (cartItems.isEmpty()) {
            throw new RuntimeException(
                    "Cart is empty"
            );
        }

        BigDecimal totalAmount = cartItems.stream()
                .map(item ->
                        item.getProduct()
                                .getPrice()
                                .multiply(
                                        BigDecimal.valueOf(
                                                item.getQuantity()
                                        )
                                )
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        Order order = Order.builder()
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .build();

        Order savedOrder =
                orderRepository.save(order);

        for (CartItem cartItem : cartItems) {

            OrderItem orderItem =
                    OrderItem.builder()
                            .order(savedOrder)
                            .product(cartItem.getProduct())
                            .quantity(
                                    cartItem.getQuantity()
                            )
                            .price(
                                    cartItem.getProduct()
                                            .getPrice()
                            )
                            .build();

            orderItemRepository.save(orderItem);
        }

        cartItemRepository.deleteAll(cartItems);

        return buildOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrders(Long customerId) {

        return orderRepository
                .findByCustomerCustomerId(customerId)
                .stream()
                .map(this::buildOrderResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: "
                                        + orderId
                        ));

        return buildOrderResponse(order);
    }

    private OrderResponse buildOrderResponse(
            Order order) {

        List<OrderItemResponse> items =
                orderItemRepository
                        .findByOrderOrderId(
                                order.getOrderId()
                        )
                        .stream()
                        .map(item ->
                                OrderItemResponse.builder()
                                        .productId(
                                                item.getProduct()
                                                        .getProductId()
                                        )
                                        .productName(
                                                item.getProduct()
                                                        .getName()
                                        )
                                        .quantity(
                                                item.getQuantity()
                                        )
                                        .price(
                                                item.getPrice()
                                        )
                                        .build()
                        )
                        .toList();

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .customerId(
                        order.getCustomer()
                                .getCustomerId()
                )
                .orderDate(order.getOrderDate())
                .totalAmount(
                        order.getTotalAmount()
                )
                .items(items)
                .build();
    }
}