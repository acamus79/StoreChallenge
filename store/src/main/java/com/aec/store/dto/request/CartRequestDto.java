package com.aec.store.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * DTO (Data Transfer Object) representing a shopping cart with input data.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representation of the shopping cart with input data.")
public class CartRequestDto {

    /**
     * A map that contains product IDs as keys and their respective quantities as values.
     */
    @NotNull(message = "Product quantity map cannot be null.")
    private Map<String, Integer> productQuantity;

    /**
     * Flag indicating whether the cart is confirmed.
     */
    @Builder.Default
    private boolean confirm = false;

}
