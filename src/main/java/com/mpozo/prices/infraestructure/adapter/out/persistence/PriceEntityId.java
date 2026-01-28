package com.mpozo.prices.infraestructure.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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
