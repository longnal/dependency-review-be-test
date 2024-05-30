package com.example.restapi.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Data
public class UpdateUserRequest {
    @JsonProperty("email")
    @NotEmpty(message = "Email is required!")
    @Email(message = "Invalid email!")
    @Length(min = 3, max = 255, message = "Email length must between 6 and 255")
    private String email;
}
