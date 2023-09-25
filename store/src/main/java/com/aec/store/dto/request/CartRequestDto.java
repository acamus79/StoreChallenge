package com.aec.store.dto.request;

import com.aec.store.enums.Role;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @Min(value = 1, message = "Minimum quantity cannot be less than 1.")
    private Map<String, Integer> productQuantity;

    @Builder.Default
    private boolean confirm = false;

}
