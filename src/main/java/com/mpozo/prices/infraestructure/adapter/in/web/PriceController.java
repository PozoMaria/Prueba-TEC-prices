package com.mpozo.prices.infraestructure.adapter.in.web;

import com.mpozo.prices.application.dto.PriceResponse;
import com.mpozo.prices.application.port.in.PriceUseCase;
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

    @GetMapping("/getPrice")
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam("brandId") Long brandId,
            @RequestParam("productId") Long productId,
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
            throw e; // otras excepciones
        }
    }
}
