package com.shopsphere.service;

import com.shopsphere.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(
            Long customerId);

    List<OrderResponse> getOrders(
            Long customerId);

    OrderResponse getOrderDetails(
            Long orderId);
}