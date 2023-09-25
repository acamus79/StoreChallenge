package com.aec.store.services;

import com.aec.store.dto.request.ProductRequestDto;
import com.aec.store.dto.response.ProductAdvancedDto;
import com.aec.store.dto.response.ProductBasicDto;
import com.aec.store.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends BasicService<ProductEntity, String>{
    Page<ProductBasicDto> getProductToUser(Pageable pageable);
    Page<ProductAdvancedDto> getProductToAdmin(Pageable pageable);
    ProductAdvancedDto saveProduct(ProductRequestDto dto);
    ProductAdvancedDto updateProduct(ProductRequestDto dto, String id);
    boolean deleteProduct(String id);
}
