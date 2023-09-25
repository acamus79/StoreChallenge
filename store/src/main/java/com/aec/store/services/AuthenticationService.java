package com.aec.store.services;

import com.aec.store.dto.request.UserLoginDto;
import com.aec.store.dto.request.UserRequestDto;

public interface AuthenticationService {
    String register(UserRequestDto request);
    String authenticate(UserLoginDto request);
}
