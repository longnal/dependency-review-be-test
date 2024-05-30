package com.example.restapi.services.interfaces;

import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.requests.CreateCategoryRequest;
import com.example.restapi.models.requests.UpdateCategoryRequest;
import com.example.restapi.models.responses.CategoryResponse;

import java.util.List;


public interface ICategoryService {
    public List<CategoryResponse> getCategories();

    public CategoryResponse getCategoryById(Long categoryId) throws AppException;

    public CategoryResponse createCategory(CreateCategoryRequest createCategoryRequest) throws AppException;

    public CategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest updateCategoryRequest) throws AppException;

    public void deleteCategory(Long categoryId) throws AppException;
}