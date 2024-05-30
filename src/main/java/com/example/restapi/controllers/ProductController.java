package com.example.restapi.controllers;

import ch.qos.logback.classic.Logger;
import com.example.restapi.exceptions.AppException;
import com.example.restapi.models.requests.CreateProductRequest;
import com.example.restapi.models.requests.UpdateProductRequest;
import com.example.restapi.models.responses.ProductResponse;
import com.example.restapi.models.responses.SuccessResponse;
import com.example.restapi.services.ProductService;
import com.example.restapi.services.interfaces.IProductService;
import jakarta.validation.Valid;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.restapi.constants.AdminConstants.ADMIN_PRODUCTS_ENDPOINT;
import static com.example.restapi.constants.ServiceConstants.API;
import static com.example.restapi.constants.ServiceConstants.VERSION_1;
import static com.example.restapi.exceptions.AppErrorMessages.PRODUCT_NOT_FOUND_MESSAGE;
import static com.example.restapi.exceptions.AppErrors.TO_DO_APP_ERROR_001;
import static com.example.restapi.exceptions.AppErrors.TO_DO_APP_ERROR_002;

@RestController
public class ProductController {

    private final IProductService productService;

    private static final Logger log = (Logger) LoggerFactory.getLogger(UserController.class);

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(API + "/" + VERSION_1 + "/" + ADMIN_PRODUCTS_ENDPOINT)
    public List<ProductResponse> getProducts() {
        try {
            log.info("Incoming request for getting all products.");
            return productService.getProducts();
        } finally {
            log.info("Processing request for getting all users finished.");
        }
    }


    @GetMapping(API + "/" + VERSION_1 + "/" + ADMIN_PRODUCTS_ENDPOINT + "/{productId}")
    public ProductResponse getProduct(@PathVariable Long productId) throws AppException {
        try {
            log.info("Incoming request for getting a product.");
            return productService.getProductById(productId);
        } catch (ResourceNotFoundException ex) {
            throw new AppException(PRODUCT_NOT_FOUND_MESSAGE, TO_DO_APP_ERROR_002);
        } finally {
            log.info("Processing request for getting a product finished.");
        }
    }


    @PostMapping(API + "/" + VERSION_1 + "/" + ADMIN_PRODUCTS_ENDPOINT)
    public ProductResponse createProduct(@RequestBody(required = true) @Valid CreateProductRequest createProductRequest, BindingResult bindingResult) throws AppException {
        try {
            if (bindingResult.hasErrors()) {
                throw new AppException(bindingResult.getAllErrors().getFirst().getDefaultMessage(), TO_DO_APP_ERROR_001);
            }
            log.info("Incoming request for product creation.");
            return productService.createProduct(createProductRequest);
        } finally {
            log.info("Processing for product creation request finished.");
        }
    }

    @PutMapping(API + "/" + VERSION_1 + "/" + ADMIN_PRODUCTS_ENDPOINT + "/{productId}")
    public ProductResponse updateProduct(@PathVariable Long productId, @RequestBody(required = true) @Valid UpdateProductRequest updateProductRequest, BindingResult bindingResult) throws AppException {
        try {
            if (bindingResult.hasErrors()) {
                throw new AppException(bindingResult.getAllErrors().getFirst().getDefaultMessage(), TO_DO_APP_ERROR_001);
            }
            log.info("Incoming request for editing a product");
            return productService.updateProduct(productId, updateProductRequest);
        } finally {
            log.info("Processing for product edit process finished.");
        }
    }

    @DeleteMapping(API + "/" + VERSION_1 + "/" + ADMIN_PRODUCTS_ENDPOINT + "/{productId}")
    public SuccessResponse deleteProduct(@PathVariable Long productId) throws AppException {
        try {
            String incommingRequestMessage = "Incoming request for deleting a product: " + productId;
            log.info(incommingRequestMessage);
            productService.deleteProduct(productId);
            ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
            return SuccessResponse.builder()
                    .errorCode(response.getStatusCode().value())
                    .message("Deleted successfully")
                    .build();
        } finally {
            log.info("Processing for deleting product process finished.");
        }
    }

}
