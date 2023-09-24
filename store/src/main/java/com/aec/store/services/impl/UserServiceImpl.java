package com.aec.store.services.impl;

import com.aec.store.dto.request.UserRegisterDto;
import com.aec.store.dto.response.AdvancedUserDto;
import com.aec.store.dto.response.BasicUserDto;
import com.aec.store.models.UserEntity;
import com.aec.store.repositories.UserRepository;
import com.aec.store.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends BasicServiceImpl<UserEntity, String, UserRepository> implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public boolean deleteUser(String id) {
        if (id != null) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public BasicUserDto updateUser(UserRegisterDto userRegisterDto, String id) {
        return null;
    }

    @Override
    public List<AdvancedUserDto> getAll() {
        return null;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
