package com.shopsphere.service;

import com.shopsphere.dto.CustomerRequest;
import com.shopsphere.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse addCustomer(
            CustomerRequest request);

    List<CustomerResponse> getAllCustomers();

    CustomerResponse getCustomerById(
            Long customerId);

    CustomerResponse updateCustomer(
            Long customerId,
            CustomerRequest request);
}