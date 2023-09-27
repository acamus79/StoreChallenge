package com.aec.store.services;

import com.aec.store.dto.request.ProductRequestDto;
import com.aec.store.dto.response.ProductAdvancedDto;
import com.aec.store.dto.response.ProductBasicDto;
import com.aec.store.models.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * The ProductService interface defines methods for managing products.
 */
public interface ProductService extends BasicService<ProductEntity, String> {

    /**
     * Retrieves a paginated list of basic product information for user display.
     *
     * @param pageable The pagination information.
     * @return A Page containing ProductBasicDto objects.
     */
    Page<ProductBasicDto> getProductToUser(Pageable pageable);

    /**
     * Retrieves a paginated list of advanced product information for admin display.
     *
     * @param pageable The pagination information.
     * @return A Page containing ProductAdvancedDto objects.
     */
    Page<ProductAdvancedDto> getProductToAdmin(Pageable pageable);

    /**
     * Saves a new product based on the provided ProductRequestDto.
     *
     * @param dto The ProductRequestDto containing product details.
     * @return The ProductAdvancedDto representing the saved product.
     */
    ProductAdvancedDto saveProduct(ProductRequestDto dto);

    /**
     * Updates an existing product with the provided details.
     *
     * @param dto The ProductRequestDto containing updated product details.
     * @param id  The ID of the product to update.
     * @return The ProductAdvancedDto representing the updated product.
     */
    ProductAdvancedDto updateProduct(ProductRequestDto dto, String id);

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     * @return true if the product was successfully deleted, false otherwise.
     */
    boolean deleteProduct(String id);
}
