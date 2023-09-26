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
/**
 * Implementation of the ProductService interface providing operations related to products.
 * This service handles operations such as retrieving products, creating, updating, and deleting products,
 * as well as providing product data for both regular users and administrators.
 */
@Service
public class ProductServiceImpl extends BasicServiceImpl<ProductEntity,String, ProductRepository> implements ProductService {

    /**
     * Constructs a new ProductServiceImpl with the specified repository.
     *
     */
    private final ProductRepository repository;
    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    /**
     * Retrieves a paginated list of basic product information suitable for regular users.
     *
     * @param pageable The Pageable object for pagination.
     * @return A Page containing basic product information.
     * @throws ResponseStatusException If no products are found.
     */
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

    /**
     * Retrieves a paginated list of advanced product information suitable for administrators.
     *
     * @param pageable The Pageable object for pagination.
     * @return A Page containing advanced product information.
     * @throws ResponseStatusException If no products are found.
     */
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

    /**
     * Creates a new product based on the provided data.
     *
     * @param dto The ProductRequestDto containing product details.
     * @return The advanced product information of the created product.
     * @throws ResponseStatusException If a product with the same name already exists or an error occurs during saving.
     */
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

    /**
     * Updates an existing product with the provided data.
     *
     * @param dto The ProductRequestDto containing updated product details.
     * @param id  The ID of the product to be updated.
     * @return The advanced product information of the updated product.
     * @throws ResponseStatusException If the product is not found.
     */
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

    /**
     * Deletes a product with the specified ID.
     *
     * @param id The ID of the product to be deleted.
     * @return True if the product is deleted successfully, false otherwise.
     * @throws ResponseStatusException If the product is not found.
     */
    @Override
    @Transactional
    public boolean deleteProduct(String id) {
        if (id != null && repository.existsById(id)) {
            this.repository.deleteById(id);
            return true;
        }
        return false;
    }

}
