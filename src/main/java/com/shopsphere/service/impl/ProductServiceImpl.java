package com.shopsphere.service.impl;

import com.shopsphere.dto.ProductRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.entity.Category;
import com.shopsphere.entity.Product;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.dto.ProductRequest;
import com.shopsphere.dto.ProductResponse;
import com.shopsphere.repository.CategoryRepository;
import com.shopsphere.repository.ProductRepository;
import com.shopsphere.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl
        implements ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse addProduct(ProductRequest request) {

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()
                        ));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(category)
                .build();

        Product savedProduct =
                productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + productId
                        ));

        return mapToResponse(product);
    }

    @Override
    public ProductResponse updateProduct(
            Long productId,
            ProductRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + productId
                        ));

        Category category = categoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()
                        ));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(category);

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product not found with id: "
                                        + productId
                        ));

        productRepository.delete(product);
    }

    @Override
    public List<ProductResponse> searchProducts(
            String keyword) {

        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private ProductResponse mapToResponse(
            Product product) {

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categoryId(
                        product.getCategory()
                                .getCategoryId()
                )
                .categoryName(
                        product.getCategory()
                                .getName()
                )
                .build();
    }
}