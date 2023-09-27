package com.aec.store.controllers;

import com.aec.store.dto.request.ProductRequestDto;
import com.aec.store.dto.response.UserAdvancedDto;
import com.aec.store.dto.response.ProductAdvancedDto;
import com.aec.store.services.CartService;
import com.aec.store.services.ProductService;
import com.aec.store.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aec.store.utils.MessageConstants.*;
import static com.aec.store.utils.ValidationUtils.handleValidationErrors;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "API endpoints for")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;

    /**
     * Get all users.
     *
     * @return ResponseEntity with a list of users
     */
    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(
            summary = "Get all users",
            description = "Get a list of all users"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "No users found"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<?> getAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserAdvancedDto> users = this.userService.getAll();
            response.put("status", "success");
            response.put("message", USER_FOUND);
            response.put("count", users.size());
            response.put("carts", users);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", USER_NOT_FOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * Get a paginated list of products for admin.
     *
     * @param page Pageable object for pagination
     * @return ResponseEntity with a paginated list of products
     */
    @GetMapping("/products")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(
            summary = "Get products for admin",
            description = "Get a paginated list of products available to administrators"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No products found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<?> getProductsAdmin(
            @PageableDefault(size = 25) Pageable page
    ) {
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("status", "success");
            response.put("message", PRODUCT_FOUND);
            response.put("products", productService.getProductToAdmin(page));
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
     * Create a new product.
     *
     * @param request ProductRequestDto containing product details
     * @param errors  Validation errors, if any
     * @return ResponseEntity with the created product details
     */
    @PostMapping("/products")
    @PreAuthorize("hasAuthority('admin:create')")
    @Operation(
            summary = "Create a new product",
            description = "Create a new product with the given details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Map<String,Object>> createProduct(
            @RequestBody @Valid ProductRequestDto request,
            Errors errors
    ){
        if(errors.hasErrors()){
            System.out.println(errors);
            return handleValidationErrors(errors);
        }

        Map<String, Object> response = new HashMap<>();
        try {
            ProductAdvancedDto prod = productService.saveProduct(request);
            response.put("status", "success");
            response.put("message", PRODUCT_REGISTERED_SUCCESSFULLY);
            response.put("product", prod);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            response.put("status", "error");
            response.put("message", "Registration failed");
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Delete a product by ID.
     *
     * @param id ID of the product to delete
     * @return ResponseEntity with status and message
     */
    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    @Operation(
            summary = "Delete a product by ID",
            description = "Delete a product with the given ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Map<String, String>> deleteProducts(@PathVariable String id){
        Map<String, String> response = new HashMap<>();
        try {
            if (productService.deleteProduct(id)) {
                response.put("status", "success");
                response.put("message", PRODUCT_REMOVED_SUCCESSFULLY);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", PRODUCT_NOT_REMOVED);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Update an existing product by ID.
     *
     * @param id      ID of the product to update
     * @param request ProductRequestDto containing updated product details
     * @param errors  Validation errors, if any
     * @return ResponseEntity with updated product details
     */
    @PutMapping("/products/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    @Operation(
            summary = "Update an existing product",
            description = "Update an existing product with the given ID and details"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<Map<String, Object>> updateProduct(
            @PathVariable String id, @Valid @RequestBody ProductRequestDto request, Errors errors
    ){
        if(errors.hasErrors()){
            return handleValidationErrors(errors);
        }
        Map<String, Object> response = new HashMap<>();
        try{
            ProductAdvancedDto dtoResponse = productService.updateProduct(request,id);
            response.put("status", "success");
            response.put("message", UPDATE_SUCCESSFUL);
            response.put("user", dtoResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("status", "error");
            response.put("message", UPDATE_FAILED);
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Get a paginated list of all shopping carts.
     *
     * @param page Pageable object for pagination
     * @return ResponseEntity with a paginated list of shopping carts
     */
    @GetMapping("/carts")
    @PreAuthorize("hasAuthority('admin:read')")
    @Operation(
            summary = "Get all shopping carts",
            description = "Get a paginated list of all shopping carts"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shopping carts retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No shopping carts found"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    public ResponseEntity<?> allCarts(@PageableDefault(size = 25) Pageable page){
        Map<String, Object> response = new HashMap<>();
        try{
            response.put("status", "success");
            response.put("message", CART_FOUND);
            response.put("products", cartService.getAllCarts(page));
            return ResponseEntity.ok(response);
        }catch (ResponseStatusException e){
            response.put("status", "error");
            response.put("message", CART_NOT_FOUND);
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception ex){
            response.put("status", "error");
            response.put("message", INTERNAL_SERVER_ERROR);
            response.put("error", ex.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
