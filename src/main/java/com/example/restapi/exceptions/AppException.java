package com.example.restapi.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AppException extends Exception {

    private AppErrors errorCode;
    private String message;

    public AppException(AppErrors errorCode) {
        this.errorCode = errorCode;
    }

    public AppException(String message, AppErrors errorCode) {
        this.errorCode = errorCode;
        this.message = message;
    }

}
