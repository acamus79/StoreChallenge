package com.aec.store.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO (Data Transfer Object) representing product data for input purposes.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product representation with input data.")
public class ProductRequestDto {

    /**
     * The ID of the product. Hidden from Swagger documentation.
     */
    @Hidden
    private String id;

    /**
     * The name of the product.
     */
    @Schema(description = "The product's name.", example = "XGt24")
    @Size(min = 2, max = 80, message = "The length of the name must be between 2 to 80 characters.")
    @NotEmpty(message = "The field must not be empty.")
    private String name;

    /**
     * The description of the product.
     */
    @Schema(description = "The product's description.", example = "Wireless mouse")
    @Size(min = 2, max = 80, message = "The length of the description must be between 2 to 80 characters.")
    private String description;

    /**
     * The price of the product.
     */
    @Schema(description = "Price of the product.",example = "19.99")
    @NotNull(message = "The price field cannot be null.")
    private BigDecimal price;

    /**
     * The quantity of the product in stock.
     */
    @Schema(description = "Quantity of the product in stock.", example = "100")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1.")
    private int quantityInStock;

}
