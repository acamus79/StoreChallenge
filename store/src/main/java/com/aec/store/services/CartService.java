package com.aec.store.services;

import com.aec.store.dto.request.CartRequestDto;
import com.aec.store.dto.response.CartResponseDto;
import com.aec.store.models.CartEntity;

public interface CartService extends BasicService<CartEntity, String>{
    CartResponseDto createOrUpdateCart(CartRequestDto dto, String userId);
    boolean deleteCart(String id);

}
