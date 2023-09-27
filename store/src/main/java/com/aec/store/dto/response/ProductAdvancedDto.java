package com.aec.store.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Detailed representation of the user with output data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detailed representation of the user with output data.")
public class ProductAdvancedDto {

    /**
     * ID of the product.
     */
    @Schema(
            description = "ID of the product.",
            example = "528f22c3-1f9c-493f-8334-c70b83b5b885"
    )
    private String id;

    /**
     * Name of the product.
     */
    @Schema(
            description = "Name of the product.",
            example = "Product Name"
    )
    private String name;

    /**
     * Description of the product.
     */
    @Schema(
            description = "Description of the product.",
            example = "Product description goes here."
    )
    private String description;

    /**
     * Price of the product.
     */
    @Schema(
            description = "Price of the product.",
            example = "19.99"
    )
    private BigDecimal price;

    /**
     * Quantity of the product in stock.
     */
    @Schema(
            description = "Quantity of the product in stock.",
            example = "100"
    )
    private int quantityInStock;

    /**
     * Timestamp indicating when the product was last updated.
     */
    @Schema(description = "Timestamp indicating when the product was last updated.")
    private LocalDateTime updatedAt;

}
