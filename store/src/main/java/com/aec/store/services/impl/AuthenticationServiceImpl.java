package com.aec.store.services.impl;

import com.aec.store.dto.request.UserLoginDto;
import com.aec.store.dto.request.UserRegisterDto;
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


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public String register(UserRegisterDto request) {

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

