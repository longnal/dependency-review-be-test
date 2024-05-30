package com.example.restapi.services;

import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.Category;
import com.example.restapi.models.requests.CreateCategoryRequest;
import com.example.restapi.models.requests.UpdateCategoryRequest;
import com.example.restapi.models.responses.CategoryResponse;
import com.example.restapi.repositories.CategoryRepository;
import com.example.restapi.services.interfaces.ICategoryService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.restapi.exceptions.AppErrorMessages.*;
import static com.example.restapi.exceptions.AppErrors.*;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<CategoryResponse> getCategories() {
        Iterable<Category> categories = this.categoryRepository.findAll();
        List<CategoryResponse> response = new ArrayList<>();
        categories.forEach(category -> response.add(CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .status(category.getStatus())
                .createdAt((category.getCreatedAt() != null) ? category.getCreatedAt().toString() : "")
                .updatedAt((category.getUpdatedAt() != null) ? category.getUpdatedAt().toString() : "")
                .build()));
        return response;
    }

    public CategoryResponse getCategoryById(Long categoryId) throws AppException {
        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category: " + categoryId));
            return CategoryResponse.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .status(category.getStatus())
                    .createdAt((category.getCreatedAt() != null) ? category.getCreatedAt().toString() : "")
                    .updatedAt((category.getUpdatedAt() != null) ? category.getUpdatedAt().toString() : "")
                    .build();
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_CATEGORY_DETAIL_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) throws AppException {
        try {
            Category createdCategory = categoryRepository.save(Category.builder()
                    .name(createCategoryRequest.getName())
                    .description(createCategoryRequest.getDescription())
                    .status(createCategoryRequest.getStatus())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build());
            String createdAt = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(createdCategory.getCreatedAt());
            String updatedAt = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(createdCategory.getUpdatedAt());

            return CategoryResponse.builder()
                    .id(createdCategory.getId())
                    .name(createdCategory.getName())
                    .description(createdCategory.getDescription())
                    .status(createdCategory.getStatus())
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_CATEGORY_CREATION_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    @Transactional
    public CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest updateCategoryRequest) throws AppException {
        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category: " + categoryId));
            category.setName(updateCategoryRequest.getName());
            category.setDescription(updateCategoryRequest.getDescription());
            category.setStatus(updateCategoryRequest.getStatus());
            category.setUpdatedAt(new Date());
            Category updatedCategory = categoryRepository.save(category);

            return CategoryResponse.builder()
                    .id(updatedCategory.getId())
                    .name(updatedCategory.getName())
                    .description(updatedCategory.getDescription())
                    .status(updatedCategory.getStatus())
                    .createdAt(updatedCategory.getCreatedAt().toString())
                    .updatedAt(updatedCategory.getUpdatedAt().toString())
                    .build();
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_CATEGORY_UPDATE_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    public void deleteCategory(Long categoryId) throws AppException {
        try {
            Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category: " + categoryId));
            categoryRepository.delete(category);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_CATEGORY_DELETE_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }
}
