package com.aec.store.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * The BasicService interface provides basic CRUD (Create, Read, Update, Delete) operations for entities.
 *
 * @param <T> The entity type.
 * @param <ID> The type of the entity's ID.
 */
public interface BasicService<T, ID> {

    /**
     * Saves an entity.
     *
     * @param t The entity to be saved.
     * @return The saved entity.
     */
    T save(T t);

    /**
     * Retrieves an entity by its ID.
     *
     * @param id The ID of the entity to retrieve.
     * @return An Optional containing the retrieved entity, or empty if not found.
     */
    Optional<T> findById(ID id);

    /**
     * Retrieves all entities.
     *
     * @return A list of all entities.
     */
    List<T> findAll();

    /**
     * Retrieves all entities with pagination.
     *
     * @param pageable The pageable object specifying pagination options.
     * @return A Page containing a subset of entities based on pagination criteria.
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Updates an existing entity.
     *
     * @param t The entity to be updated.
     * @return The updated entity.
     */
    T edit(T t);

    /**
     * Deletes an entity.
     *
     * @param t The entity to be deleted.
     */
    void delete(T t);

    /**
     * Deletes an entity by its ID.
     *
     * @param id The ID of the entity to be deleted.
     */
    void deleteById(ID id);

    /**
     * Checks if an entity with a given ID exists.
     *
     * @param id The ID to check.
     * @return true if an entity with the ID exists, false otherwise.
     */
    boolean existById(ID id);
}
