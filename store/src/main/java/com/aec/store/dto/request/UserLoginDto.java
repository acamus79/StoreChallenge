package com.aec.store.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) representing input data required for authentication.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representation of the input data required for authentication.")
public class UserLoginDto {

    /**
     * The user's email address.
     */
    @Email(message = "Must be a properly formatted email address.")
    @NotEmpty(message = "The field must not be empty.")
    @Size(max = 255)
    private String email;

    /**
     * The user's password.
     */
    @Size(min = 8, max = 15, message = "Min 8, max 15 characters in password")
    @NotEmpty(message = "The field must not be empty.")
    private String password;
}
