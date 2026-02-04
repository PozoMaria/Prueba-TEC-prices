package com.mpozo.prices.infraestructure.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Price response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "PriceResponse",
        description = "Response object with the applicable price for a product from a brand and a specific date and time"
)
public class PriceResponse {

    @Schema(
            description = "Unique identifier of the product",
            example = "35455"
    )
    private Integer productId;

    @Schema(
            description = "Unique identifier of the brand (e.g., 1 = ZARA)",
            example = "1"
    )
    private Integer brandId;

    @Schema(
            description = "Identifier of the price list applied",
            example = "2"
    )
    private Integer priceList;

    @Schema(
            description = "Start date and time when the price is applicable (ISO 8601 format)",
            example = "2020-06-14T15:00:00"
    )
    private LocalDateTime startDate;

    @Schema(
            description = "End date and time when the price is applicable (ISO 8601 format)",
            example = "2020-06-14T18:30:00"
    )
    private LocalDateTime endDate;

    @Schema(
            description = "Final price to be applied for the product in the given currency",
            example = "25.45"
    )
    private BigDecimal finalPrice;

}
