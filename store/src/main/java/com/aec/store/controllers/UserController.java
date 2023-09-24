package com.aec.store.controllers;

import com.aec.store.dto.request.UserRegisterDto;
import com.aec.store.dto.response.BasicUserDto;
import com.aec.store.models.UserEntity;
import com.aec.store.services.JwtService;
import com.aec.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.aec.store.utils.ValidationUtils.handleValidationErrors;



@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API endpoints for managing user data")
public class UserController {

    public static final String DELETE_USER = "User deleted";
    public static final String NO_DELETE_USER = "The user has not been deleted";

    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Delete the currently logged-in user.
     *
     * @param request HttpServletRequest containing user's authorization token
     * @return ResponseEntity with status and message
     */
    @DeleteMapping
    @Operation(summary = "Delete the currently logged-in user", description = "Delete the currently logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Map<String, String>> deleteCurrentUser(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring("Bearer ".length());
            String username = jwtService.extractUsername(token);
            Optional<UserEntity> userOptional = userService.findByEmail(username);
            if (userOptional.isPresent() && userService.deleteUser(userOptional.get().getId())) {
                Map<String, String> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", DELETE_USER);
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", NO_DELETE_USER);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Update an existing user by ID.
     *
     * @param userRegisterDtoForm User registration data for update
     * @param id                 ID of the user to update
     * @param errors             Validation errors, if any
     * @return ResponseEntity with updated user details
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user", description = "Update an existing user account by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Map<String, Object>> update(
            @Valid @RequestBody UserRegisterDto userRegisterDtoForm,
            @PathVariable String id,
            Errors errors) {
        ResponseEntity<Map<String, Object>> validationErrorsResponse = handleValidationErrors(errors);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        Map<String, Object> response = new HashMap<>();
        try {
            BasicUserDto basicUserDto = this.userService.updateUser(userRegisterDtoForm, id);
            response.put("status", "success");
            response.put("message", "User updated successfully");
            response.put("user", basicUserDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Registration failed");
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }


    /**
     * Get the currently logged-in user.
     *
     * @param request HttpServletRequest containing user's authorization token
     * @return ResponseEntity with the current user's details
     */
    @GetMapping("/current")
    @Operation(summary = "Get the currently logged-in user", description = "Get details of the currently logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getCurrentUser(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring("Bearer ".length());
            String username = jwtService.extractUsername(token);
            Optional<UserEntity> userOptional = userService.findByEmail(username);
            Map<String, Object> response = new HashMap<>();
            if (userOptional.isPresent()) {
                response.put("status", "success");
                response.put("message", "User details retrieved successfully");
                response.put("user", userService.getUserById(userOptional.get().getId()));
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
