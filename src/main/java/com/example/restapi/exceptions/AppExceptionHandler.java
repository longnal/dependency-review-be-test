package com.example.restapi.exceptions;

import com.example.restapi.models.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({AppException.class})
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        return switch (ex.getErrorCode()) {
            case TO_DO_APP_ERROR_001 -> {
                ResponseEntity<String> unProcessableEntity = new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
                yield new ResponseEntity<>(
                        ErrorResponse.builder()
                                .code(unProcessableEntity.getStatusCode().value())
                                .message(ex.getMessage())
                                .build(),
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }
            case TO_DO_APP_ERROR_002 -> {
                ResponseEntity<String> notFound = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                yield new ResponseEntity<>(
                        ErrorResponse.builder()
                                .code(notFound.getStatusCode().value())
                                .message(ex.getMessage())
                                .build(),
                        HttpStatus.NOT_FOUND
                );
            }
            case TO_DO_APP_ERROR_004 -> {
                ResponseEntity<String> conflict = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                yield new ResponseEntity<>(
                        ErrorResponse.builder()
                                .code(conflict.getStatusCode().value())
                                .message(ex.getMessage())
                                .build(),
                        HttpStatus.BAD_REQUEST
                );
            }
            default -> {
                ResponseEntity<String> internalServerError = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                yield new ResponseEntity<>(
                        ErrorResponse.builder()
                                .code(internalServerError.getStatusCode().value())
                                .message(ex.getMessage())
                                .build(),
                        HttpStatus.INTERNAL_SERVER_ERROR
                );
            }
        };

    }
}