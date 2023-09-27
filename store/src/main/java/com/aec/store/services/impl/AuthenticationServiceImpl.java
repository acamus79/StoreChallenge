package com.aec.store.services.impl;

import com.aec.store.dto.request.UserLoginDto;
import com.aec.store.dto.request.UserRequestDto;
import com.aec.store.enums.Role;
import com.aec.store.models.UserEntity;
import com.aec.store.repositories.UserRepository;
import com.aec.store.services.AuthenticationService;
import com.aec.store.services.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implementation of the {@link AuthenticationService} interface for user authentication and registration.
 * This service provides methods for user registration and authentication, including generating JWT tokens
 * for authenticated users.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registers a new user with the provided user details.
     *
     * @param request The user registration request containing user details.
     * @return A JWT token generated for the registered user.
     * @throws ResponseStatusException If the user's email already exists in the database.
     */
    @Override
    @Transactional
    public String register(UserRequestDto request) {

        if (this.repository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email " + request.getEmail() + " already exists");
        }

        var user = UserEntity.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .active(Boolean.TRUE)
                .build();
        repository.save(user);
        return jwtService.generateToken(user);
    }

    /**
     * Authenticates a user with the provided login credentials (email and password).
     *
     * @param request The user login request containing login credentials.
     * @return A JWT token generated for the authenticated user.
     * @throws ResponseStatusException If the user is not registered or if the provided credentials are invalid.
     */
    @Override
    public String authenticate(UserLoginDto request) {

        if (!this.repository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The user " + request.getEmail() + " is not registered");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail()).orElseThrow();

        return jwtService.generateToken(user);
    }
}

