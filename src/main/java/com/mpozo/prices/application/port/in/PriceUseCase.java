package com.mpozo.prices.application.port.in;

import com.mpozo.prices.application.dto.PriceResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PriceUseCase {
    PriceResponse getPrice(Long brandId, Long productId, LocalDateTime applicationDate);
}
