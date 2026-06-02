package com.shopsphere.service.impl;

import com.shopsphere.dto.CustomerRequest;
import com.shopsphere.dto.CustomerResponse;
import com.shopsphere.repository.CustomerRepository;
import com.shopsphere.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.shopsphere.dto.CustomerRequest;
import com.shopsphere.dto.CustomerResponse;
import com.shopsphere.entity.Customer;
import com.shopsphere.exception.DuplicateResourceException;
import com.shopsphere.exception.ResourceNotFoundException;

import java.util.List;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl
        implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse addCustomer(
            CustomerRequest request) {

        if (customerRepository.existsByEmail(
                request.getEmail())) {

            throw new DuplicateResourceException(
                    "Customer email already exists"
            );
        }

        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(
            Long customerId) {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + customerId
                        ));

        return mapToResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(
            Long customerId,
            CustomerRequest request) {

        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + customerId
                        ));

        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());

        Customer updatedCustomer =
                customerRepository.save(customer);

        return mapToResponse(updatedCustomer);
    }


    private CustomerResponse mapToResponse(
            Customer customer) {

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
    }
}