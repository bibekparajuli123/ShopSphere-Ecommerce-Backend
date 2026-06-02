package com.shopsphere.controller;

import com.shopsphere.dto.CategoryRequest;
import com.shopsphere.dto.CategoryResponse;
import com.shopsphere.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(
           @Valid @RequestBody CategoryRequest request) {

        CategoryResponse response =
                categoryService.addCategory(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
           @Valid @RequestBody CategoryRequest request) {

        return ResponseEntity.ok(
                categoryService.updateCategory(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id) {

        categoryService.deleteCategory(id);

        return ResponseEntity.ok(
                "Category deleted successfully"
        );
    }

}