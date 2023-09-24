package com.aec.store.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User representation with input data.")
public class UserRegisterDto {

    @Schema(description = "The user's first name.", example = "Jhon")
    @Size(min = 3, max = 80, message = "The name must contain at least 3 and no more than 80 letters.")
    private String firstname;

    @Schema(description = "The user's last name.", example = "Doe")
    @Size(min = 3, max = 80, message = "Last name must contain at least 3 and no more than 80 letters.")
    private String lastname;

    @NotBlank
    @Size(max = 60, message = "The maximum size for the name is sixty characters")
    @Email(message = "Must be a correctly formatted e-mail address")
    @Schema(description = "The e-mail address to be used by the user.", example = "jhondoe@mail.com")
    private String email;

    @NotBlank
    @Size(min = 6, max = 25, message = "Password size should be between 6 and 25 characters")
    @Schema(description = "The password that the user will have.", example = "MySuperSecretPwd@2023")
    private String password;
}
