package com.aec.store.controllers;

import com.aec.store.dto.response.AdvancedUserDto;
import com.aec.store.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "API endpoints for")
public class AdminController {

    public static final String DELETE_USER = "User deleted";
    public static final String NO_DELETE_USER = "The user has not been deleted";

    private final UserService userService;
    @GetMapping("/users/all")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<AdvancedUserDto>> getAll() {
        try {
            return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        try {
            if (userService.deleteUser(id)) {
                return new ResponseEntity<>(DELETE_USER, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(NO_DELETE_USER, HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }
}
