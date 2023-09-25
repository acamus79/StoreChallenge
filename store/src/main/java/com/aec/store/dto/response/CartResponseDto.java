package com.aec.store.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Detailed representation of the shopping cart with output data.")
public class CartResponseDto {
    @Schema(description = "Unique cart ID.", example = "528f22c3-1f9c-493f-8334-c70b83b5b885")
    private String id;

    @Schema(description = "Unique ID of the cart owner.", example = "528f22c3-1f9c-493f-8334-c70b83b5b885")
    private String userId;

    @Schema(description = "Quantity of each product in the cart.")
    private Map<String, Integer> productQuantity;

    @Schema(description = "Cart total in local currency.", example = "99.99")
    private BigDecimal amount;

    @Schema(description = "Indicates if the cart has been confirmed.", example = "true")
    private boolean confirm;

    @Schema(description = "Date and time of cart creation.")
    private LocalDateTime createdAt;

    @Schema(description = "Date and time of the last update of the cart.")
    private LocalDateTime updatedAt;
}
