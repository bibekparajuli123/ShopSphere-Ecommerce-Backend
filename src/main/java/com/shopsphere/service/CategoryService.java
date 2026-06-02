package com.shopsphere.service;

import com.shopsphere.dto.CategoryRequest;
import com.shopsphere.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest request);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long categoryId);

    CategoryResponse updateCategory(Long categoryId,
                                    CategoryRequest request);

    void deleteCategory(Long categoryId);
}