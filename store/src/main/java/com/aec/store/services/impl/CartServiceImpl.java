package com.aec.store.services.impl;

import com.aec.store.dto.request.CartRequestDto;
import com.aec.store.dto.response.CartResponseDto;
import com.aec.store.models.CartEntity;
import com.aec.store.models.ProductEntity;
import com.aec.store.models.UserEntity;
import com.aec.store.repositories.CartRepository;
import com.aec.store.repositories.ProductRepository;
import com.aec.store.repositories.UserRepository;
import com.aec.store.services.CartService;
import com.aec.store.utils.ObjectMapperUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CartServiceImpl extends BasicServiceImpl<CartEntity,String, CartRepository> implements CartService {

    private final CartRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository repository, UserRepository userRepository, ProductRepository productRepository) {
        super(repository);
        this.repository = repository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CartResponseDto createOrUpdateCart(CartRequestDto dto, String userId) {
        Optional<CartEntity> optionalCart = repository.findByUserIdAndConfirmIsFalse(userId);
        CartEntity cartEntity;

        if (optionalCart.isPresent()) {
            cartEntity = optionalCart.get();
        } else {
            cartEntity = new CartEntity();
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            cartEntity.setUserId(userEntity);
        }

        Map<String, Integer> productQuantityMap = dto.getProductQuantity();

        // Obtener la lista de IDs de productos
        List<String> productIds = new ArrayList<>(productQuantityMap.keySet());

        // Obtener todos los productos por sus IDs en una sola consulta
        List<ProductEntity> products = productRepository.findAllByIdIn(productIds);

        Map<ProductEntity, Integer> productQuantity = new HashMap<>();

        for (ProductEntity product : products) {
            String productId = product.getId();
            int quantity = productQuantityMap.get(productId);

            productQuantity.put(product, quantity);
        }

        cartEntity.setProductQuantity(productQuantity);
        repository.save(cartEntity);
        return ObjectMapperUtils.map(cartEntity, CartResponseDto.class);
    }





    @Override
    public boolean deleteCart(String id) {
        return false;
    }


}
