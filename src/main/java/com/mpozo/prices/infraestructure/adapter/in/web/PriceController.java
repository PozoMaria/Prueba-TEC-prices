package com.mpozo.prices.infraestructure.adapter.in.web;

import com.mpozo.prices.application.dto.PriceResponse;
import com.mpozo.prices.application.port.in.PriceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceUseCase priceUseCase;

    public PriceController(PriceUseCase priceUseCase) {
        this.priceUseCase = priceUseCase;
    }

    @Operation(
            summary = "Get the applicable price for a product and brand at a specific date and time",
            description = "Returns the price that would apply for a product under a brand at a given moment. " +
                    "If multiple prices are valid, the one with the highest priority is applied."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Price found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponse.class))
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "No applicable price found",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid parameters",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/getPrice")
    public ResponseEntity<PriceResponse> getPrice(
            @Parameter(description = "Brand ID", required = true, in = ParameterIn.QUERY)
            @RequestParam("brandId") Long brandId,

            @Parameter(description = "Product ID", required = true, in = ParameterIn.QUERY)
            @RequestParam("productId") Long productId,

            @Parameter(description = "Date and time for price application in ISO 8601 format (e.g., 2020-06-14T16:00:00)", required = true, in = ParameterIn.QUERY)
            @RequestParam("applicationDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate
    ) {
        try {
            PriceResponse response = priceUseCase.getPrice(brandId, productId, applicationDate);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            if ("No price found".equals(e.getMessage())) {
                return ResponseEntity.noContent().build(); // 204 No Content
            }
            throw e; // other exceptions
        }
    }
}
