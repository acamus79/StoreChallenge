package com.aec.store.dto.request;

import com.aec.store.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User representation with input data.")
public class UserRegisterDto {

    @Hidden
    private String id;

    @Schema(description = "The user's first name.", example = "Jhon")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "The first name cannot contain numbers or characters other than letters")
    @Size(min = 2, max = 30, message = "The length of the first name must be between 2 to 30 characters.")
    private String firstname;

    @Schema(description = "The user's last name.", example = "Doe")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "The last name cannot contain numbers or characters other than letters")
    @Size(min = 2, max = 30, message = "The length of the last name must be between 2 to 30 characters.")
    private String lastname;

    @NotBlank(message = "The email field cannot be empty")
    @Email(message = "Must be a correctly formatted e-mail address")
    @NotEmpty(message = "The field must not be empty.")
    @Schema(description = "The e-mail address to be used by the user.", example = "jhondoe@mail.com")
    private String email;

    @NotBlank(message = "The password field cannot be empty")
    @Size(min = 8, message = "Min 8 characters in password")
    @NotEmpty(message = "The field must not be empty.")
    @Schema(description = "The password that the user will have.", example = "MySuperSecretPwd@2023")
    private String password;

    @Builder.Default
    @Hidden
    private Role role = Role.USER;
}
