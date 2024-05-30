package com.example.restapi.controllers;

import ch.qos.logback.classic.Logger;
import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.requests.CreateCategoryRequest;
import com.example.restapi.models.requests.UpdateCategoryRequest;
import com.example.restapi.models.responses.CategoryResponse;
import com.example.restapi.models.responses.SuccessResponse;
import com.example.restapi.services.CategoryService;
import com.example.restapi.services.interfaces.ICategoryService;
import jakarta.validation.Valid;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.restapi.constants.AdminConstants.ADMIN_CATEGORIES_ENDPOINT;
import static com.example.restapi.constants.ServiceConstants.API;
import static com.example.restapi.constants.ServiceConstants.VERSION_1;
import static com.example.restapi.exceptions.AppErrorMessages.*;
import static com.example.restapi.exceptions.AppErrors.*;

@RestController
public class CategoryController {


    private final ICategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);
    @GetMapping(API + "/" + VERSION_1 + "/" + ADMIN_CATEGORIES_ENDPOINT)
    public List<CategoryResponse> getCategories() throws AppException {
        try {
            log.info("Incoming request for getting list categories.");
            return categoryService.getCategories();
        } finally {
            log.info("Processing request for getting list categories finished.");
        }
    }


    @GetMapping(API + "/" + VERSION_1 + "/" + ADMIN_CATEGORIES_ENDPOINT + "/{categoryId}")
    public CategoryResponse getCategory(@PathVariable Long categoryId) throws AppException {
        try {
            log.info("Incoming request for getting a category.");
            return categoryService.getCategoryById(categoryId);
        } catch (ResourceNotFoundException ex) {
            throw new AppException(CATEGORY_NOT_FOUND_MESSAGE, TO_DO_APP_ERROR_002);
        } finally {
            log.info("Processing request for getting a category finished.");
        }
    }


    @PostMapping(API + "/" + VERSION_1 + "/" + ADMIN_CATEGORIES_ENDPOINT)
    public CategoryResponse createCategory(@RequestBody(required = true) @Valid CreateCategoryRequest createCategoryRequest, BindingResult bindingResult) throws AppException {
        try {
            if (bindingResult.hasErrors()) {
                throw new AppException(bindingResult.getAllErrors().getFirst().getDefaultMessage(), TO_DO_APP_ERROR_001);
            }
            log.info("Incoming request for category creation.");
            return categoryService.createCategory(createCategoryRequest);
        } finally {
            log.info("Processing for category creation request finished.");
        }
    }

    @PutMapping(API + "/" + VERSION_1 + "/" + ADMIN_CATEGORIES_ENDPOINT + "/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable Long categoryId, @RequestBody(required = true) @Valid UpdateCategoryRequest updateCategoryRequest, BindingResult bindingResult) throws AppException {
        try {
            if (bindingResult.hasErrors()) {
                throw new AppException(bindingResult.getAllErrors().getFirst().getDefaultMessage(), TO_DO_APP_ERROR_001);
            }
            log.info("Incoming request for category edit");
            return categoryService.updateCategory(categoryId, updateCategoryRequest);
        } finally {
            log.info("Processing for category edit process finished.");
        }
    }

    @DeleteMapping(API + "/" + VERSION_1 + "/" + ADMIN_CATEGORIES_ENDPOINT + "/{categoryId}")
    public SuccessResponse deleteCategory(@PathVariable Long categoryId) throws AppException {
        try {
            log.info("Incoming request for deleting category: " + categoryId);
            categoryService.deleteCategory(categoryId);
            ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
            return SuccessResponse.builder()
                    .errorCode(response.getStatusCode().value())
                    .message("Deleted successfully")
                    .build();
        } finally {
            log.info("Processing for category delete process finished.");
        }
    }
}
