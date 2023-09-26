package com.aec.store.services;

import com.aec.store.dto.request.CartRequestDto;
import com.aec.store.dto.response.CartResponseDto;
import com.aec.store.models.CartEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartService extends BasicService<CartEntity, String>{
    CartResponseDto createOrUpdateCart(CartRequestDto dto, String userId);
    boolean deleteCart(String id);
    CartResponseDto findByUserId(String userId);
    List<CartResponseDto> findAllConfirmByUserId(String userId);
    Page<CartResponseDto> getAllCarts(Pageable pageable);
}
