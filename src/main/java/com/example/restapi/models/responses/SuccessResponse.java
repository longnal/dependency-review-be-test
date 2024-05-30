package com.example.restapi.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class SuccessResponse {

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("message")
    private String message;
}
