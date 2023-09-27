package com.aec.store.services;

import com.aec.store.dto.request.CartRequestDto;
import com.aec.store.dto.response.CartResponseDto;
import com.aec.store.models.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The CartService interface defines methods for managing shopping carts.
 */
public interface CartService extends BasicService<CartEntity, String> {

    /**
     * Creates or updates a shopping cart for a user.
     *
     * @param dto    The CartRequestDto containing cart details.
     * @param userId The ID of the user associated with the cart.
     * @return The CartResponseDto representing the created or updated cart.
     */
    CartResponseDto createOrUpdateCart(CartRequestDto dto, String userId);

    /**
     * Deletes a shopping cart by its ID.
     *
     * @param id The ID of the cart to delete.
     * @return true if the cart was successfully deleted, false otherwise.
     */
    boolean deleteCart(String id);

    /**
     * Finds a shopping cart by user ID.
     *
     * @param userId The ID of the user whose cart to find.
     * @return The CartResponseDto representing the user's cart.
     */
    CartResponseDto findByUserId(String userId);

    /**
     * Finds all confirmed shopping carts for a user.
     *
     * @param userId The ID of the user for whom to find confirmed carts.
     * @return A list of CartResponseDto objects representing confirmed carts.
     */
    List<CartResponseDto> findAllConfirmByUserId(String userId);

    /**
     * Retrieves a paginated list of all shopping carts.
     *
     * @param pageable The pagination information.
     * @return A Page containing CartResponseDto objects representing shopping carts.
     */
    Page<CartResponseDto> getAllCarts(Pageable pageable);
}
