package com.aec.store.services.impl;

import com.aec.store.dto.request.UserRegisterDto;
import com.aec.store.dto.response.AdvancedUserDto;
import com.aec.store.dto.response.BasicUserDto;
import com.aec.store.exceptions.ArgumentRequiredException;
import com.aec.store.models.UserEntity;
import com.aec.store.repositories.UserRepository;
import com.aec.store.services.UserService;
import com.aec.store.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl extends BasicServiceImpl<UserEntity, String, UserRepository> implements UserService {

    public static final String IS_REQUIRED_OR_DOESNT_EXIST = "User id is required or does not exist";
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
    @Transactional
    public BasicUserDto updateUser(UserRegisterDto dto, String id) {
        System.out.println(id);
        if (this.findById(id).isPresent()){
            UserEntity user = this.findById(id).get();
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setUpdatedAt(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(dto.getRole());
            this.save(user);
            return ObjectMapperUtils.map(user, BasicUserDto.class);
        }else {
            throw new ArgumentRequiredException(IS_REQUIRED_OR_DOESNT_EXIST);
        }
    }

    @Override
    public AdvancedUserDto getUserById(String id) {
        if (this.findById(id).isPresent()){
            UserEntity userFind = this.findById(id).get();
            return ObjectMapperUtils.map(userFind, AdvancedUserDto.class)  ;
        }else {
            throw new ArgumentRequiredException(IS_REQUIRED_OR_DOESNT_EXIST);
        }
    }

    @Override
    public List<AdvancedUserDto> getAll() {
        List<AdvancedUserDto> usersDto = ObjectMapperUtils.mapAll(this.findAll(), AdvancedUserDto.class);
        if(usersDto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else {
            return usersDto;
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
