package com.aec.store.services.impl;

import com.aec.store.dto.request.UserRequestDto;
import com.aec.store.dto.response.UserAdvancedDto;
import com.aec.store.dto.response.UserBasicDto;
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

import java.util.List;
import java.util.Optional;

import static com.aec.store.utils.MessageConstants.IS_REQUIRED_OR_DOESNT_EXIST;

/**
 * Implementation of the UserService interface providing operations related to user management.
 * This service handles operations such as retrieving users, creating, updating, and deleting users.
 */
@Service
public class UserServiceImpl extends BasicServiceImpl<UserEntity, String, UserRepository> implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new UserServiceImpl with the specified UserRepository and PasswordEncoder.
     *
     * @param userRepository  The UserRepository to be used for data access.
     * @param passwordEncoder The PasswordEncoder for encoding user passwords.
     */
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Deletes a user with the specified ID.
     *
     * @param id The ID of the user to be deleted.
     * @return True if the user is deleted successfully, false otherwise.
     * @throws ResponseStatusException If the user is not found.
     */
    @Override
    @Transactional
    public boolean deleteUser(String id) {
        if (id != null && userRepository.existsById(id)) {
            this.userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Updates an existing user with the provided data.
     *
     * @param dto The UserRequestDto containing updated user details.
     * @param id  The ID of the user to be updated.
     * @return The basic user information of the updated user.
     * @throws ArgumentRequiredException If the user is not found.
     */
    @Override
    @Transactional
    public UserBasicDto updateUser(UserRequestDto dto, String id) {
        if (this.findById(id).isPresent()){
            UserEntity user = this.findById(id).get();
            user.setFirstname(dto.getFirstname());
            user.setLastname(dto.getLastname());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRole(dto.getRole());
            this.save(user);
            return ObjectMapperUtils.map(user, UserBasicDto.class);
        }else {
            throw new ArgumentRequiredException(IS_REQUIRED_OR_DOESNT_EXIST);
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The basic user information of the user.
     * @throws ArgumentRequiredException If the user is not found.
     */
    @Override
    public UserBasicDto getUserById(String id) {
        if (this.findById(id).isPresent()){
            UserEntity userFind = this.findById(id).get();
            return ObjectMapperUtils.map(userFind, UserBasicDto.class)  ;
        }else {
            throw new ArgumentRequiredException(IS_REQUIRED_OR_DOESNT_EXIST);
        }
    }

    /**
     * Retrieves a list of all users with advanced user information.
     *
     * @return A list of UserAdvancedDto objects.
     * @throws ResponseStatusException If no users are found.
     */
    @Override
    public List<UserAdvancedDto> getAll() {
        List<UserAdvancedDto> usersDto = ObjectMapperUtils.mapAll(this.findAll(), UserAdvancedDto.class);
        if(usersDto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else {
            return usersDto;
        }
    }

    /**
     * Finds a user by email.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the user entity, if found.
     */
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
