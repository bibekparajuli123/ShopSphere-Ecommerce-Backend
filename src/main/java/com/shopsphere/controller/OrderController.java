package com.shopsphere.controller;

import com.shopsphere.dto.OrderResponse;
import com.shopsphere.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/place/{customerId}")
    public ResponseEntity<OrderResponse>
    placeOrder(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                orderService.placeOrder(
                        customerId
                )
        );
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>>
    getOrders(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                orderService.getOrders(customerId)
        );
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>
    getOrderDetails(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.getOrderDetails(orderId)
        );
    }
}