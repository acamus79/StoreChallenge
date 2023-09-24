package com.aec.store.controllers;

import com.aec.store.dto.response.AdvancedUserDto;
import com.aec.store.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
@Tag(name = "Admin Controller", description = "API endpoints for")
public class AdminController {

    private final UserService userService;
    @GetMapping("/all-users")
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<AdvancedUserDto>> getAll() {
        try {
            return new ResponseEntity<>(this.userService.getAll(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }
}
