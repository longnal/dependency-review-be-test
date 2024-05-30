package com.example.restapi.services.interfaces;

import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.requests.CreateCategoryRequest;
import com.example.restapi.models.requests.CreateProductRequest;
import com.example.restapi.models.requests.UpdateCategoryRequest;
import com.example.restapi.models.requests.UpdateProductRequest;
import com.example.restapi.models.responses.CategoryResponse;
import com.example.restapi.models.responses.ProductResponse;

import java.util.List;


public interface IProductService {
    public List<ProductResponse> getProducts();

    public ProductResponse getProductById(Long productId) throws AppException;

    public ProductResponse createProduct(CreateProductRequest createProductRequest) throws AppException;

    public ProductResponse updateProduct(Long categoryId, UpdateProductRequest updateProductRequest) throws AppException;

    public void deleteProduct(Long productId) throws AppException;
}