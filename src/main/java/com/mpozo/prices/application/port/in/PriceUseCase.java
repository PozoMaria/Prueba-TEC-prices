package com.mpozo.prices.application.port.in;

import com.mpozo.prices.application.dto.PriceResponse;

import java.time.LocalDateTime;

/**
 * The interface Price use case.
 */
public interface PriceUseCase {
    /**
     * Gets price.
     *
     * @param brandId         the brand id
     * @param productId       the product id
     * @param applicationDate the application date
     * @return the price
     */
    PriceResponse getPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
