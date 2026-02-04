package com.mpozo.prices.application.service;

import com.mpozo.prices.infraestructure.dto.PriceResponse;

import java.time.LocalDateTime;

public interface PriceService {
    PriceResponse getPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
