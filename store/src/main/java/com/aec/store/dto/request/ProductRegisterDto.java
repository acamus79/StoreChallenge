package com.aec.store.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Product representation with input data.")
public class ProductRegisterDto {
    @Hidden
    private String id;

    @Schema(description = "The product's name.", example = "XGt24")
    @Size(min = 2, max = 80, message = "The length of the name must be between 2 to 80 characters.")
    @NotEmpty(message = "The field must not be empty.")
    private String name;

    @Schema(description = "The product's description.", example = "Wireless mouse")
    @Size(min = 2, max = 80, message = "The length of the description must be between 2 to 80 characters.")
    private String description;

    @Schema(description = "Price of the product.",example = "19.99")
    @NotNull(message = "The price field cannot be null.")
    private BigDecimal price;

    @Schema(description = "Quantity of the product in stock.", example = "100")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1.")
    private int quantityInStock;

}
