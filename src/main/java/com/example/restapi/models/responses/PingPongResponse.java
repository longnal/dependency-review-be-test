package com.example.restapi.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PingPongResponse {

    @JsonProperty("error_code")
    private int errorCode;

    @JsonProperty("message")
    private String message;
}
