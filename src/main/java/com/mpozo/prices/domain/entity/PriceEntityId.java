package com.mpozo.prices.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The type Price entity id.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceEntityId implements Serializable {

    private Integer brandId;
    private Integer priceList;
    private Integer productId;
    private LocalDateTime startDate;
}
