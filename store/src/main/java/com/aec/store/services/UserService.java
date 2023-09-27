package com.aec.store.services;

import com.aec.store.dto.request.UserRequestDto;
import com.aec.store.dto.response.UserAdvancedDto;
import com.aec.store.dto.response.UserBasicDto;
import com.aec.store.models.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * The UserService interface defines methods for managing user-related operations.
 */
public interface UserService extends BasicService<UserEntity, String> {

    /**
     * Deletes a user by their ID.
     *
     * @param id The ID of the user to delete.
     * @return true if the user was successfully deleted, false otherwise.
     */
    boolean deleteUser(String id);

    /**
     * Updates an existing user with the provided details.
     *
     * @param dto The UserRequestDto containing updated user details.
     * @param id  The ID of the user to update.
     * @return The UserBasicDto representing the updated user.
     */
    UserBasicDto updateUser(UserRequestDto dto, String id);

    /**
     * Retrieves a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The UserBasicDto representing the retrieved user.
     */
    UserBasicDto getUserById(String id);

    /**
     * Retrieves a list of advanced user information.
     *
     * @return A List of UserAdvancedDto objects.
     */
    List<UserAdvancedDto> getAll();

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return An Optional containing the UserEntity if found, otherwise empty.
     */
    Optional<UserEntity> findByEmail(String email);
}
