package com.shopsphere.service;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse addProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long productId);

    ProductResponse updateProduct(
            Long productId,
            ProductRequest request);

    void deleteProduct(Long productId);

    List<ProductResponse> searchProducts(
            String keyword);
}