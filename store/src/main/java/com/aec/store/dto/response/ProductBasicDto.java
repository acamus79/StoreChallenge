package com.aec.store.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Basic representation of the Product with output data.")
public class ProductBasicDto {

    @Schema(
            description = "ID of the product.",
            example = "528f22c3-1f9c-493f-8334-c70b83b5b885"
    )
    private String id;

    @Schema(
            description = "Name of the product.",
            example = "Product Name"
    )
    private String name;

    @Schema(
            description = "Description of the product.",
            example = "Product description goes here."
    )
    private String description;

    @Schema(
            description = "Price of the product.",
            example = "19.99"
    )
    private BigDecimal price;

}
