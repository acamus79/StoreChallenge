package com.aec.store.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User representation with output data.")
public class UserLoginDto {

    @Email(message = "Must be a properly formatted email address.")
    @NotEmpty(message = "The field must not be empty.")
    @Size(max = 255)
    private String email;

    @Size(min = 8, max = 15, message = "Min 8, max 15 characters in password")
    @NotEmpty(message = "The field must not be empty.")
    private String password;
}

