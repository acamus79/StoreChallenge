package com.aec.store.controllers;

import com.aec.store.dto.request.UserRegisterDto;
import com.aec.store.dto.response.AdvancedUserDto;
import com.aec.store.dto.response.BasicUserDto;
import com.aec.store.models.UserEntity;
import com.aec.store.services.JwtService;
import com.aec.store.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "API endpoints for")
public class UserController {

    public static final String DELETE_USER = "User deleted";
    public static final String NO_DELETE_USER = "The user has not been deleted";

    private final UserService userService;
    private final JwtService jwtService;

    @DeleteMapping("/{id}")
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

    @PutMapping("/{id}")
    public ResponseEntity<BasicUserDto> update(@Valid @RequestBody UserRegisterDto userRegisterDtoForm, @PathVariable String id) {
        try {
            BasicUserDto basicUserDto = this.userService.updateUser(userRegisterDtoForm, id);
            return new ResponseEntity<>(basicUserDto, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/current")
    public ResponseEntity<AdvancedUserDto> getCurrentUser(HttpServletRequest request) {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            String token = authorizationHeader.substring("Bearer ".length());
            String username = jwtService.extractUsername(token);
            Optional<UserEntity> userOptional = userService.findByEmail(username);
            return userOptional.map(userEntity -> ResponseEntity.ok(userService.getUserById(userEntity.getId()))).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
