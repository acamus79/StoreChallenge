package com.aec.store.controllers;

import com.aec.store.dto.request.CartRequestDto;
import com.aec.store.dto.request.UserRequestDto;
import com.aec.store.dto.response.CartResponseDto;
import com.aec.store.dto.response.UserBasicDto;
import com.aec.store.models.UserEntity;
import com.aec.store.services.CartService;
import com.aec.store.services.JwtService;
import com.aec.store.services.ProductService;
import com.aec.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.aec.store.utils.MessageConstants.*;
import static com.aec.store.utils.ValidationUtils.handleValidationErrors;



@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API endpoints for client users.")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final ProductService productService;
    private final CartService cartService;

    /**
     * Delete the currently logged-in user.
     *
     * @param request HttpServletRequest containing user's authorization token
     * @return ResponseEntity with status and message
     */
    @DeleteMapping
    @Operation(
            summary = "Deletes the logged-in user.",
            description = "Delete the currently logged-in user."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Map<String, String>> deleteCurrentUser(HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring("Bearer ".length());
            String userId = jwtService.extractClaim(token, claims -> claims.get("id", String.class));
            Optional<UserEntity> userOptional = userService.findById(userId);
            if (userOptional.isPresent() && userService.deleteUser(userId)) {
                response.put("status", "success");
                response.put("message", USER_DELETED);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", NO_DELETE_USER);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Update an existing user by ID.
     *
     * @param userRequestDtoForm User registration data for update
     * @param id                 ID of the user to update
     * @param errors             Validation errors, if any
     * @return ResponseEntity with updated user details
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing user",
            description = "Update an existing user account by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<Map<String, Object>> update(
            @Valid @RequestBody UserRequestDto userRequestDtoForm,
            @PathVariable String id,
            Errors errors) {
        ResponseEntity<Map<String, Object>> validationErrorsResponse = handleValidationErrors(errors);
        if (validationErrorsResponse != null) {
            return validationErrorsResponse;
        }

        Map<String, Object> response = new HashMap<>();
        try {
            UserBasicDto userBasicDto = this.userService.updateUser(userRequestDtoForm, id);
            response.put("status", "success");
            response.put("message", USER_UPDATED);
            response.put("user", userBasicDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", USER_NOT_UPDATED);
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get the currently logged-in user.
     *
     * @param authToken RequestHeader containing user's authorization token
     * @return ResponseEntity with the current user's details
     */
    @GetMapping("/current")
    @Operation(
            summary = "Get the currently logged-in user",
            description = "Get details of the currently logged-in user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader("Authorization") String authToken) {
        Map<String, Object> response = new HashMap<>();
        try {
            String token = authToken.substring("Bearer ".length());
            String username = jwtService.extractUsername(token);
            Optional<UserEntity> userOptional = userService.findByEmail(username);
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
            response.put("status", "error");
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get a paginated list of products for the currently logged-in user.
     *
     * @param page Pageable object for pagination
     * @return ResponseEntity with a paginated list of ProductBasicDto objects
     */
    @GetMapping("/products")
    @Operation(
            summary = "Get a paginated list of products for the currently logged-in user",
            description = "Get a paginated list of products available to the currently logged-in user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> getProductsUser(
            @PageableDefault(size = 25) Pageable page
    ) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("status", "success");
            response.put("message", PRODUCT_FOUND);
            response.put("products", productService.getProductToUser(page));
            return ResponseEntity.ok(response);
        }catch (ResponseStatusException e){
            response.put("status", "error");
            response.put("message", PRODUCT_NOT_FOUND);
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.put("status", "error");
            response.put("message", INTERNAL_SERVER_ERROR);
            response.put("error", ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Create or update a user's shopping cart.
     *
     * @param dto      CartRequestDto containing cart details
     * @param authToken RequestHeader containing user's authorization token
     * @param errors   Validation errors, if any
     * @return ResponseEntity with cart details
     */
    @PostMapping("/cart")
    @Operation(
            summary = "Create or update a user's shopping cart",
            description = "Create or update a shopping cart for the currently logged-in user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping cart created or updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> createOrUpdateCart(
            @RequestBody @Valid CartRequestDto dto,
            @RequestHeader("Authorization") String authToken, Errors errors
    ) {
        if(errors.hasErrors()){
            return handleValidationErrors(errors);
        }
        Map<String, Object> response = new HashMap<>();
        if (dto.getProductQuantity().isEmpty()) {
            response.put("status", "error");
            response.put("message", PRODUCT_QUANTITY_EMPTY);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        try {
            String token = authToken.substring("Bearer ".length());
            String userId = jwtService.extractClaim(token, claims -> claims.get("id", String.class));
            response.put("status", "success");
            response.put("message", CART_CREATED);
            response.put("cart", cartService.createOrUpdateCart(dto, userId));

            return ResponseEntity.ok(response);
        }catch (ResponseStatusException e){
            response.put("status", "error");
            response.put("message", REGISTRATION_FAILED);
            response.put("error", e.getMessage().split("\"")[1]);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.put("status", "error");
            response.put("message", INTERNAL_SERVER_ERROR);
            response.put("error", ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Delete a user's shopping cart by ID.
     *
     * @param id ID of the shopping cart to delete
     * @return ResponseEntity with status and message
     */
    @DeleteMapping("/cart/{id}")
    @Operation(
            summary = "Delete a user's shopping cart by ID",
            description = "Delete a shopping cart for the currently logged-in user by its ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping cart deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Shopping cart not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> deleteCart(@PathVariable String id){

        Map<String, String> response = new HashMap<>();
        try {
            if (cartService.deleteCart(id)) {
                response.put("status", "success");
                response.put("message", CART_DELETED);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", CART_NOT_DELETED);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get the confirmation false shopping cart for the currently logged-in user.
     *
     * @param authToken RequestHeader containing user's authorization token
     * @return ResponseEntity with the user's shopping cart details
     */
    @GetMapping("/cart")
    @Operation(
            summary = "Obtain the pending shopping cart for the currently logged in user.",
            description = "Get the details of the shopping cart for the currently logged-in user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping cart details retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Shopping cart not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> getCartForCurrentUser(@RequestHeader("Authorization") String authToken){
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("status", "success");
            response.put("message", CART_FOUND);
            response.put("cart", cartService.findByUserId(extractUserIdFromToken(authToken)));
            return ResponseEntity.ok(response);
        }catch (ResponseStatusException e){
            response.put("status", "error");
            response.put("message", USER_HAS_NO_ACTIVE_CART);
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.put("status", "error");
            response.put("message", INTERNAL_SERVER_ERROR);
            response.put("error", ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Get confirmed purchases for the currently logged-in user.
     *
     * @param authToken RequestHeader containing user's authorization token
     * @return ResponseEntity with confirmed purchases details
     */
    @GetMapping("/cart/confirm")
    @Operation(
            summary = "Get confirmed purchases for the currently logged-in user",
            description = "Get confirmed purchases for the currently logged-in user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Confirmed purchases retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No confirmed purchases found"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public ResponseEntity<?> getCartConfirmForCurrentUser(@RequestHeader("Authorization") String authToken){
        Map<String, Object> response = new HashMap<>();
        try{
            List<CartResponseDto> carts =cartService.findAllConfirmByUserId(extractUserIdFromToken(authToken));
            response.put("status", "success");
            response.put("message", CONFIRMED_PURCHASES_FOUND);
            response.put("count", carts.size());
            response.put("carts", carts);
            return ResponseEntity.ok(response);
        }catch (ResponseStatusException e){
            response.put("status", "error");
            response.put("message", USER_HAS_NO_CONFIRMED_PURCHASES);
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.put("status", "error");
            response.put("message", INTERNAL_SERVER_ERROR);
            response.put("error", ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Extract the user ID from the authorization token.
     *
     * @param authToken RequestHeader containing user's authorization token
     * @return Extracted user ID as a string
     */
    private String extractUserIdFromToken(String authToken) {
        return jwtService.extractClaim(
                authToken.substring("Bearer ".length()),
                claims -> claims.get("id", String.class)
        );
    }

}
