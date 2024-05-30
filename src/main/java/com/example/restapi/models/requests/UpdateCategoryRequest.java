package com.example.restapi.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Data
public class UpdateCategoryRequest {
    @JsonProperty("name")
    @NotEmpty(message = "Name is required!")
    @Length(min = 3, max = 255, message = "Name length must between 6 and 255")
    private String name;

    @JsonProperty("description")
    @NotEmpty(message = "Description is required!")
    @Length(min = 3, max = 500, message = "Name length must between 6 and 500")
    private String description;

    @JsonProperty("status")
    @NotNull(message = "Status is not null!")
    @Min(value = 0, message = "Must be 0 or 1!")
    @Max(value = 1, message = "Must be 0 or 1")
    private int status;
}
