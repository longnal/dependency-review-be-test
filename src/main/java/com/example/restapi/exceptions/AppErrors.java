package com.example.restapi.exceptions;

import lombok.Getter;

@Getter
public enum AppErrors {

    TO_DO_APP_ERROR_001("Invalid value"),
    TO_DO_APP_ERROR_002("Resource not found"),
    TO_DO_APP_ERROR_003("Internal server error"),
    TO_DO_APP_ERROR_004("Duplicate found");

    private final String message;

    AppErrors(String message) {
        this.message = message;
    }

}
