package com.mpozo.prices.application.port.out.persistence;

import com.mpozo.prices.application.port.out.LoadPricePort;
import com.mpozo.prices.domain.model.Price;
import com.mpozo.prices.infraestructure.adapter.out.persistence.PriceEntityRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Price persistence adapter.
 */
@Component
public class PricePersistenceAdapter implements LoadPricePort {

    private final PriceEntityRepository repository;

    /**
     * Instantiates a new Price persistence adapter.
     *
     * @param repository the repository
     */
    public PricePersistenceAdapter(PriceEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Price> findPricesByBrandAndProduct(Long brandId, Long productId, LocalDateTime applicationDate) {
        return repository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        brandId.intValue(),
                        productId.intValue(),
                        applicationDate,
                        applicationDate
                )
                .stream()
                .map(PriceMapper::toDomain) // PriceEntity -> Price (domain)
                .toList();
    }
}
