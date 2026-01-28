package com.mpozo.prices.application.port.out;

import com.mpozo.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface LoadPricePort {

    /**
     * Obtiene todas las tarifas de un producto y marca aplicables para la fecha indicada
     */
    List<Price> findPricesByBrandAndProduct(Long brandId, Long productId, LocalDateTime applicationDate);
}
