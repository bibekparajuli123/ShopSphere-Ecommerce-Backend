package com.shopsphere.service.impl;

import com.shopsphere.entity.Category;
import com.shopsphere.dto.CategoryRequest;
import com.shopsphere.dto.CategoryResponse;
import com.shopsphere.exception.DuplicateResourceException;
import com.shopsphere.exception.ResourceNotFoundException;
import com.shopsphere.repository.CategoryRepository;
import com.shopsphere.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {

        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "Category already exists"
            );
        }

        Category category = Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return mapToResponse(savedCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category -> CategoryResponse.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .description(category.getDescription())
                        .build())
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + categoryId
                        ));

        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId,
                                           CategoryRequest request) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + categoryId
                        ));

        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category updatedCategory = categoryRepository.save(category);

        return mapToResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + categoryId
                        ));

        categoryRepository.delete(category);
    }


    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}