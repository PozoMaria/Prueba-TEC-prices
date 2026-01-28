package com.mpozo.prices.application.port.out;

import com.mpozo.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Load price port.
 */
public interface LoadPricePort {

    /**
     * Obtiene todas las tarifas de un producto y marca aplicables para la fecha indicada
     *
     * @param brandId         the brand id
     * @param productId       the product id
     * @param applicationDate the application date
     * @return the list
     */
    List<Price> findPricesByBrandAndProduct(Long brandId, Long productId, LocalDateTime applicationDate);
}
