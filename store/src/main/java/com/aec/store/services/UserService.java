package com.aec.store.services;

import com.aec.store.dto.request.UserRequestDto;
import com.aec.store.dto.response.UserAdvancedDto;
import com.aec.store.dto.response.UserBasicDto;
import com.aec.store.models.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService extends BasicService<UserEntity, String>{
    boolean deleteUser(String id);
    UserBasicDto updateUser(UserRequestDto dto, String id);
    UserBasicDto getUserById(String id);
    List<UserAdvancedDto> getAll();
    Optional<UserEntity> findByEmail(String email);


}
