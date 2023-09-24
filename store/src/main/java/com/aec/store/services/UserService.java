package com.aec.store.services;

import com.aec.store.dto.request.UserRegisterDto;
import com.aec.store.dto.response.AdvancedUserDto;
import com.aec.store.dto.response.BasicUserDto;
import com.aec.store.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService extends BasicService<UserEntity, String>{
    boolean deleteUser(String id);
    BasicUserDto updateUser(UserRegisterDto userRegisterDto, String id);
    List<AdvancedUserDto> getAll();
    Optional<UserEntity> findByEmail(String email);

}
