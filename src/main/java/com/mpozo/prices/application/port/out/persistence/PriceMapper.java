package com.mpozo.prices.application.port.out.persistence;

import com.mpozo.prices.domain.model.Price;
import com.mpozo.prices.infraestructure.adapter.out.persistence.PriceEntity;

/**
 * The type Price mapper.
 */
public class PriceMapper {

    /**
     * Convierte de PriceEntity (DB) a Price (dominio)
     *
     * @param entity the entity
     * @return the price
     */
    public static Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getProductId().intValue(),
                entity.getBrandId().intValue(),
                entity.getPriceList(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriority(),
                entity.getPrice()
        );
    }

    /**
     * Opcional: convierte de Price (dominio) a PriceEntity
     *
     * @param price the price
     * @return the price entity
     */
    public static PriceEntity toEntity(Price price) {
        PriceEntity entity = new PriceEntity();
        entity.setProductId(price.getProductId().intValue());
        entity.setBrandId(price.getBrandId().intValue());
        entity.setPriceList(price.getPriceList());
        entity.setStartDate(price.getStartDate());
        entity.setEndDate(price.getEndDate());
        entity.setPriority(price.getPriority());
        entity.setPrice(price.getFinalPrice());
        return entity;
    }
}
