package com.aec.store.controllers;

import com.aec.store.dto.request.ProductRequestDto;
import com.aec.store.dto.response.UserAdvancedDto;
import com.aec.store.dto.response.ProductAdvancedDto;
import com.aec.store.services.ProductService;
import com.aec.store.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aec.store.utils.ValidationUtils.handleValidationErrors;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "API endpoints for")
public class AdminController {

    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<UserAdvancedDto>> getAll() {
        try {
            return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<Page<ProductAdvancedDto> > getProductsAdmin(
            @PageableDefault(size = 25) Pageable page
    ) {
        return ResponseEntity.ok(productService.getProductToAdmin(page));
    }

    @PostMapping("/products")
    @PreAuthorize("hasAuthority('admin:create')")
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
            response.put("message", "Product registered successfully");
            response.put("product", prod);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (Exception e){
            response.put("status", "error");
            response.put("message", "Registration failed");
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<Map<String, String>> deleteProducts(@PathVariable String id){
        Map<String, String> response = new HashMap<>();
        try {
            if (productService.deleteProduct(id)) {
                response.put("status", "success");
                response.put("message", "The product was correctly removed");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Product could not be removed");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/products/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
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
            response.put("message", "User updated successfully");
            response.put("user", dtoResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            response.put("status", "error");
            response.put("message", "Update failed");
            response.put("error", e.getMessage().split("\"")[1]);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
