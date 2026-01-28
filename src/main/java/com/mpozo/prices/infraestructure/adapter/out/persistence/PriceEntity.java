package com.mpozo.prices.infraestructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Price entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PRICES")
@IdClass(PriceEntityId.class)
public class PriceEntity {

    @Id
    @Column(name = "BRAND_ID", nullable = false)
    private Integer brandId;

    @Id
    @Column(name = "PRICE_LIST", nullable = false)
    private Integer priceList;

    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    private Integer productId;

    @Id
    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "PRIORITY", nullable = false)
    private Integer priority;

    @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "CURR", nullable = false, length = 3)
    private String curr;

}
