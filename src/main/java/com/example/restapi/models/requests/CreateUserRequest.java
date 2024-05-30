package com.example.restapi.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

}
