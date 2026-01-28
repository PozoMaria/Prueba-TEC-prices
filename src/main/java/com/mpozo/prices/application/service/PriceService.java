package com.mpozo.prices.application.service;

import com.mpozo.prices.application.port.in.PriceUseCase;
import com.mpozo.prices.application.port.out.LoadPricePort;
import com.mpozo.prices.application.dto.PriceResponse;
import com.mpozo.prices.domain.model.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * The type Price service.
 */
@Service
public class PriceService implements PriceUseCase {

    private final LoadPricePort loadPricePort;

    /**
     * Instantiates a new Price service.
     *
     * @param loadPricePort the load price port
     */
    public PriceService(LoadPricePort loadPricePort) {
        this.loadPricePort = loadPricePort;
    }

    @Override
    public PriceResponse getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        Price price = loadPricePort.findPricesByBrandAndProduct(brandId, productId, applicationDate)
                .stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new RuntimeException("No price found"));

        return new PriceResponse(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getFinalPrice()
        );
    }
}
