package com.aec.store.services.impl;

import com.aec.store.dto.request.ProductRequestDto;
import com.aec.store.dto.response.ProductAdvancedDto;
import com.aec.store.dto.response.ProductBasicDto;
import com.aec.store.models.ProductEntity;
import com.aec.store.repositories.ProductRepository;
import com.aec.store.services.ProductService;
import com.aec.store.utils.ObjectMapperUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl extends BasicServiceImpl<ProductEntity,String, ProductRepository> implements ProductService {

    private final ProductRepository repository;
    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Page<ProductBasicDto> getProductToUser(Pageable pageable) {
        List<ProductEntity> productEntities = repository.findAll();
        List<ProductBasicDto> response;

        if(!productEntities.isEmpty()){
            response = ObjectMapperUtils.mapAll(productEntities, ProductBasicDto.class);
            final int star = (int) pageable.getOffset();
            final int end = Math.min((star + pageable.getPageSize()), response.size());
            return new PageImpl<>(response.subList(star, end), pageable, response.size());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Products registered");
        }
    }

    @Override
    public Page<ProductAdvancedDto> getProductToAdmin(Pageable pageable) {
        List<ProductEntity> productEntities = repository.findAll();
        List<ProductAdvancedDto> response;

        if(!productEntities.isEmpty()){
            response = ObjectMapperUtils.mapAll(productEntities, ProductAdvancedDto.class);
            final int star = (int) pageable.getOffset();
            final int end = Math.min((star + pageable.getPageSize()), response.size());
            return new PageImpl<>(response.subList(star, end), pageable, response.size());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Products registered");
        }
    }

    @Override
    @Transactional
    public ProductAdvancedDto saveProduct(ProductRequestDto dto) {

        if (repository.existsByNameAndSoftDeleteFalse(dto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with the same name already exists");
        }
        try{
            ProductEntity productEntity = ObjectMapperUtils.map(dto, ProductEntity.class);
            repository.save(productEntity);
            return ObjectMapperUtils.map(productEntity, ProductAdvancedDto.class);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Error saving the product");
        }
    }

    @Override
    @Transactional
    public ProductAdvancedDto updateProduct(ProductRequestDto dto, String id) {
        Optional<ProductEntity> op = repository.findById(id);
        if (op.isPresent()){
            ProductEntity productEntity = op.get();
            productEntity.setName(dto.getName());
            productEntity.setDescription(dto.getDescription());
            productEntity.setPrice(dto.getPrice());
            productEntity.setQuantityInStock(dto.getQuantityInStock());
            this.edit(productEntity);
            return ObjectMapperUtils.map(productEntity, ProductAdvancedDto.class);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not Found");
        }
    }

    @Override
    public boolean deleteProduct(String id) {
        if (id != null && repository.existsById(id)) {
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }

}
