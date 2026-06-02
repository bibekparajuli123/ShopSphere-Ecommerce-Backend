package com.shopsphere.controller;

import com.shopsphere.dto.CustomerRequest;
import com.shopsphere.dto.CustomerResponse;
import com.shopsphere.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse>
    addCustomer(
            @Valid @RequestBody CustomerRequest request) {

        return new ResponseEntity<>(
                customerService.addCustomer(request),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>>
    getAllCustomers() {

        return ResponseEntity.ok(
                customerService.getAllCustomers()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse>
    getCustomerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomerById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse>
    updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomer(
                        id,
                        request
                )
        );
    }
}