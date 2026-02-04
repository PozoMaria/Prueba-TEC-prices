package com.mpozo.prices.application.service.impl;

import com.mpozo.prices.application.service.PriceService;
import com.mpozo.prices.domain.mapper.PriceMapper;
import com.mpozo.prices.domain.repository.PriceEntityRepository;
import com.mpozo.prices.infraestructure.dto.PriceResponse;
import com.mpozo.prices.domain.model.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;

/**
 * The type Price service.
 */
@Service
public class PriceServiceImpl implements PriceService {

    private final PriceEntityRepository priceRepository;
    private final PriceMapper priceMapper;

    /**
     * Instantiates a new Price service.
     *
     * @param priceRepository the load price port
     * @param priceMapper
     */
    public PriceServiceImpl(PriceEntityRepository priceRepository, PriceMapper priceMapper) {
        this.priceRepository = priceRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public PriceResponse getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {
        Price price = priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(brandId.intValue(), productId.intValue(), applicationDate, applicationDate)
                .stream()
                .map(priceMapper::toDomain)
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
