package com.aec.store.services.impl;

import com.aec.store.dto.request.CartRequestDto;
import com.aec.store.dto.response.CartResponseDto;
import com.aec.store.exceptions.InsufficientStockException;
import com.aec.store.models.CartEntity;
import com.aec.store.models.ProductEntity;
import com.aec.store.models.UserEntity;
import com.aec.store.repositories.CartRepository;
import com.aec.store.repositories.ProductRepository;
import com.aec.store.repositories.UserRepository;
import com.aec.store.services.CartService;
import com.aec.store.utils.ObjectMapperUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;
/**
 * Service implementation for managing shopping carts.
 * This service provides methods to create, update, delete, and retrieve shopping carts,
 * as well as calculate cart totals and manage cart products.
 */
@Service
@Transactional
public class CartServiceImpl extends BasicServiceImpl<CartEntity,String, CartRepository> implements CartService {

    private final CartRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /**
     * Constructor for CartServiceImpl.
     *
     * @param repository        The CartRepository to use.
     * @param userRepository    The UserRepository to use.
     * @param productRepository The ProductRepository to use.
     */
    public CartServiceImpl(CartRepository repository, UserRepository userRepository, ProductRepository productRepository) {
        super(repository);
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    /**
     * Create or update a shopping cart for a user.
     *
     * @param dto    The CartRequestDto containing cart details.
     * @param userId The ID of the user associated with the cart.
     * @return A CartResponseDto representing the created or updated cart.
     * @throws ResponseStatusException If the user is not found or there is insufficient stock for products.
     */
    @Override
    public CartResponseDto createOrUpdateCart(CartRequestDto dto, String userId) {
        // Attempt to find an existing cart for the user
        Optional<CartEntity> optionalCart = repository.findByUserIdAndConfirmIsFalseAndSoftDeleteIsFalse(userId);
        CartEntity cartEntity;
        // If a cart exists, use it; otherwise, create a new cart
        if (optionalCart.isPresent()) {
            cartEntity = optionalCart.get();
        } else {
            cartEntity = new CartEntity();
            // Retrieve user information or throw an exception if the user is not found
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            cartEntity.setUserId(userEntity.getId());
        }
        // Extract product quantities from the DTO
        Map<String, Integer> productQuantityMap = dto.getProductQuantity();
        // Retrieve product information based on product IDs
        List<String> productIds = new ArrayList<>(productQuantityMap.keySet());
        List<ProductEntity> products = productRepository.findAllByIdIn(productIds);
        Map<ProductEntity, Integer> productQuantity = new HashMap<>();

        try{
            // Calculate cart totals and check for insufficient stock
            for (ProductEntity product : products) {
                if(!product.decreaseStock(productQuantityMap.get(product.getId()))){
                    // Throw an exception if there's insufficient stock for a product
                    throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
                }
                productQuantity.put(product, productQuantityMap.get(product.getId()));
            }
        }catch (InsufficientStockException e){
            // Handle insufficient stock exception by returning an appropriate HTTP status and message
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        // Set cart properties and save it
        cartEntity.setProductQuantity(productQuantity);
        cartEntity.setAmount(calculateCartTotal(productQuantity));
        repository.save(cartEntity);
        // Map the cart entity to a DTO and return it
        return ObjectMapperUtils.map(cartEntity, CartResponseDto.class);
    }

    /**
     * Delete a shopping cart by ID.
     *
     * @param id The ID of the cart to delete.
     * @return True if the cart was deleted successfully, false otherwise.
     * @throws ResponseStatusException If the cart is confirmed or not found.
     */
    @Override
    public boolean deleteCart(String id) {
        Optional<CartEntity> optionalCart = repository.findById(id);

        if (optionalCart.isPresent()) {
            CartEntity cartEntity = optionalCart.get();

            if (cartEntity.getConfirm()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot delete a confirmed cart.");
            }

            Map<ProductEntity, Integer> productQuantity = cartEntity.getProductQuantity();

            for (Map.Entry<ProductEntity, Integer> entry : productQuantity.entrySet()) {
                ProductEntity product = entry.getKey();
                Integer quantityInCart = entry.getValue();

                product.increaseStock(quantityInCart);
            }

            repository.delete(cartEntity);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Find a shopping cart by user ID.
     *
     * @param userId The ID of the user to find the cart for.
     * @return A CartResponseDto representing the found cart.
     * @throws ResponseStatusException If the cart is not found.
     */
    @Override
    public CartResponseDto findByUserId(String userId) {
        Optional<CartEntity> op = repository.findByUserIdAndConfirmIsFalseAndSoftDeleteIsFalse(userId);
        if(op.isPresent()){
            return ObjectMapperUtils.map(op.get(), CartResponseDto.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found.");
        }
    }

    /**
     * Find all confirmed shopping carts by user ID.
     *
     * @param userId The ID of the user to find the carts for.
     * @return A list of CartResponseDto objects representing the confirmed carts.
     * @throws ResponseStatusException If no carts are found for the user.
     */
    @Override
    public List<CartResponseDto> findAllConfirmByUserId(String userId) {
        List<CartEntity> listCart = repository.findByUserIdAndConfirmIsTrue(userId);
        if(listCart.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found.");
        }else {
            return ObjectMapperUtils.mapAll(listCart, CartResponseDto.class);
        }
    }

    /**
     * Get a paginated list of all shopping carts.
     *
     * @param pageable The Pageable object for pagination.
     * @return A Page containing CartResponseDto objects representing the shopping carts.
     * @throws ResponseStatusException If no carts are registered.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CartResponseDto> getAllCarts(Pageable pageable) {

        List<CartEntity> cartsEntities = repository.findAll();
        List<CartResponseDto> response;

        if(!cartsEntities.isEmpty()){
            response = ObjectMapperUtils.mapAll(cartsEntities, CartResponseDto.class);
            final int star = (int) pageable.getOffset();
            final int end = Math.min((star + pageable.getPageSize()), response.size());
            return new PageImpl<>(response.subList(star, end), pageable, response.size());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Carts registered");
        }

    }

    /**
     * Calculate the total amount of a shopping cart based on the products and their quantities.
     *
     * @param productQuantity A map containing products and their respective quantities
     * @return The total amount as a BigDecimal
     */
    private BigDecimal calculateCartTotal(Map<ProductEntity, Integer> productQuantity) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        // Iterates through each product and its quantity in the cart
        for (Map.Entry<ProductEntity, Integer> entry : productQuantity.entrySet()) {
            ProductEntity product = entry.getKey();
            Integer quantity = entry.getValue();
            // Retrieves the price of the product and calculates the total for that product
            BigDecimal productPrice = product.getPrice();
            BigDecimal productTotal = productPrice.multiply(BigDecimal.valueOf(quantity));
            // Adds the product total to the overall total amount
            totalAmount = totalAmount.add(productTotal);
        }
        return totalAmount;
    }

}
