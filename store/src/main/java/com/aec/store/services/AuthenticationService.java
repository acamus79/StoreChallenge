package com.aec.store.services;

import com.aec.store.dto.request.UserLoginDto;
import com.aec.store.dto.request.UserRequestDto;

/**
 * The AuthenticationService interface defines methods for user authentication and registration.
 */
public interface AuthenticationService {

    /**
     * Registers a new user with the provided user request data.
     *
     * @param request The user request data for registration.
     * @return A JWT token representing the user's authentication.
     */
    String register(UserRequestDto request);

    /**
     * Authenticates a user with the provided user login data.
     *
     * @param request The user login data for authentication.
     * @return A JWT token representing the user's authentication.
     */
    String authenticate(UserLoginDto request);
}
