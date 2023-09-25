package com.aec.store.controllers;

import com.aec.store.dto.request.UserLoginDto;
import com.aec.store.dto.request.UserRequestDto;
import com.aec.store.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.aec.store.utils.ValidationUtils.handleValidationErrors;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "API endpoints for user authentication and registration")
public class AuthController {

    private final AuthenticationService service;

    /**
     * Register a new user.
     *
     * @param request User registration data
     * @param errors  Validation errors, if any
     * @return ResponseEntity with registration status and token
     */
    @PostMapping("/singUp")
    @Operation(summary = "Register a new user", description = "Create a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Registration failed or Validation failed")
    })
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody @Valid UserRequestDto request,
            Errors errors
    ) {
        ResponseEntity<Map<String, Object>> validationErrorsResponse = handleValidationErrors(errors);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        Map<String, Object> response = new HashMap<>();
        try {
            String registrationResult = service.register(request);

            response.put("status", "success");
            response.put("message", "User registered successfully");
            response.put("token", registrationResult);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Registration failed");
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Authenticate a user.
     *
     * @param request User login data
     * @param errors  Validation errors, if any
     * @return ResponseEntity with authentication status and token
     */
    @PostMapping("/signIn")
    @Operation(summary = "Authenticate a user", description = "Authenticate a user with provided credentials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful"),
            @ApiResponse(responseCode = "401", description = "Authentication failed"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<Map<String, Object>> authenticate(
            @RequestBody @Valid UserLoginDto request, Errors errors
    ) {
        ResponseEntity<Map<String, Object>> validationErrorsResponse = handleValidationErrors(errors);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        Map<String, Object> response = new HashMap<>();
        try {
            String authenticationResult = service.authenticate(request);

            if (authenticationResult.equals("Authentication failed")) {
                response.put("status", "error");
                response.put("message", "Authentication failed");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

            response.put("status", "success");
            response.put("message", "Authentication successful");
            response.put("token", authenticationResult);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Authentication failed");
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
