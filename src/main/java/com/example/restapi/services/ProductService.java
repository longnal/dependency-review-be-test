package com.example.restapi.services;

import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.Category;
import com.example.restapi.models.Product;
import com.example.restapi.models.requests.CreateCategoryRequest;
import com.example.restapi.models.requests.CreateProductRequest;
import com.example.restapi.models.requests.UpdateCategoryRequest;
import com.example.restapi.models.requests.UpdateProductRequest;
import com.example.restapi.models.responses.CategoryResponse;
import com.example.restapi.models.responses.ProductResponse;
import com.example.restapi.repositories.CategoryRepository;
import com.example.restapi.repositories.ProductRepository;
import com.example.restapi.services.interfaces.ICategoryService;
import com.example.restapi.services.interfaces.IProductService;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.restapi.exceptions.AppErrorMessages.*;
import static com.example.restapi.exceptions.AppErrors.TO_DO_APP_ERROR_003;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResponse> getProducts() {
        Iterable<Product> products = this.productRepository.findAll();
        List<ProductResponse> response = new ArrayList<>();
        products.forEach(product -> response.add(ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .thumbnail(product.getThumbnail())
                .status(product.getStatus())
                .createdBy(product.getCreatedBy())
                .createdAt((product.getCreatedAt() != null) ? product.getCreatedAt().toString() : "")
                .updatedAt((product.getUpdatedAt() != null) ? product.getUpdatedAt().toString() : "")
                .build()));
        return response;
    }

    public ProductResponse getProductById(Long productId) throws AppException {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product: " + productId));
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .thumbnail(product.getThumbnail())
                    .status(product.getStatus())
                    .createdBy(product.getCreatedBy())
                    .createdAt((product.getCreatedAt() != null) ? product.getCreatedAt().toString() : "")
                    .updatedAt((product.getUpdatedAt() != null) ? product.getUpdatedAt().toString() : "")
                    .build();
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_PRODUCT_DETAIL_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest createProductRequest) throws AppException {
        try {
            Product createdProduct = productRepository.save(Product.builder()
                    .name(createProductRequest.getName())
                    .description(createProductRequest.getDescription())
                    .thumbnail(createProductRequest.getThumbnail())
                    .status(createProductRequest.getStatus())
                    .createdBy(createProductRequest.getCreatedBy())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build());
            String createdAt = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(createdProduct.getCreatedAt());
            String updatedAt = new SimpleDateFormat("yyyy-MM-dd H:m:s").format(createdProduct.getUpdatedAt());

            return ProductResponse.builder()
                    .id(createdProduct.getId())
                    .name(createdProduct.getName())
                    .description(createdProduct.getDescription())
                    .status(createdProduct.getStatus())
                    .createdAt(createdAt)
                    .updatedAt(updatedAt)
                    .build();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_PRODUCT_CREATION_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    @Transactional
    public ProductResponse updateProduct(Long productId, UpdateProductRequest updateProductRequest) throws AppException {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product: " + productId));
            product.setName(updateProductRequest.getName());
            product.setDescription(updateProductRequest.getDescription());
            product.setThumbnail(updateProductRequest.getThumbnail());
            product.setStatus(updateProductRequest.getStatus());
            product.setCreatedBy(updateProductRequest.getCreatedBy());
            product.setUpdatedAt(new Date());
            Product updatedProduct = productRepository.save(product);

            return ProductResponse.builder()
                    .id(updatedProduct.getId())
                    .name(updatedProduct.getName())
                    .description(updatedProduct.getDescription())
                    .thumbnail(updatedProduct.getThumbnail())
                    .status(updatedProduct.getStatus())
                    .createdBy(updatedProduct.getCreatedBy())
                    .createdAt(updatedProduct.getCreatedAt().toString())
                    .updatedAt(updatedProduct.getUpdatedAt().toString())
                    .build();
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_PRODUCT_UPDATE_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }

    public void deleteProduct(Long productId) throws AppException {
        try {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product: " + productId));
            productRepository.delete(product);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new AppException(UNKNOWN_PRODUCT_DELETE_MESSAGE, TO_DO_APP_ERROR_003);
        }
    }
}
