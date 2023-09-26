package com.aec.store.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Representation of the shopping cart with input data.")
public class CartRequestDto {

    @NotNull(message = "Product quantity map cannot be null.")
    private Map<String, Integer> productQuantity;

    @Builder.Default
    private boolean confirm = false;

}