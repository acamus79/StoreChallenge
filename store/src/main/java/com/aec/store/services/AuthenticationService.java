package com.aec.store.services;

import com.aec.store.dto.request.UserLoginDto;
import com.aec.store.dto.request.UserRegisterDto;

public interface AuthenticationService {
    String register(UserRegisterDto request);
    String authenticate(UserLoginDto request);
}
