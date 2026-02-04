package com.mpozo.prices.domain.repository;

import com.mpozo.prices.domain.entity.PriceEntity;
import com.mpozo.prices.domain.entity.PriceEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Price entity repository.
 */
@Repository
public interface PriceEntityRepository extends JpaRepository<PriceEntity, PriceEntityId> {
    /**
     * Find by brand id and product id and start date less than equal and end date greater than equal list.
     *
     * @param brandId   the brand id
     * @param productId the product id
     * @param startDate the start date
     * @param endDate   the end date
     * @return the list
     */
    List<PriceEntity> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(int brandId, int productId, LocalDateTime startDate, LocalDateTime endDate);
}
