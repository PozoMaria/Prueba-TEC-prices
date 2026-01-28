package com.mpozo.prices.application.port.out.persistence;

import com.mpozo.prices.domain.model.Price;
import com.mpozo.prices.infraestructure.adapter.out.persistence.PriceEntity;
import com.mpozo.prices.infraestructure.adapter.out.persistence.PriceEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PricePersistenceAdapterTest {

    private PriceEntityRepository repository;
    private PricePersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PriceEntityRepository.class);
        adapter = new PricePersistenceAdapter(repository);
    }

    @Test
    void findPricesByBrandAndProduct_ReturnsMappedPrices() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        PriceEntity entity1 = new PriceEntity();
        entity1.setBrandId(1);
        entity1.setProductId(35455);
        entity1.setPriceList(2);
        entity1.setPriority(1);
        entity1.setPrice(BigDecimal.valueOf(25.45));
        entity1.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0));
        entity1.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30));

        // Mock repository
        when(repository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                anyInt(), anyInt(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(entity1));

        // Act
        List<Price> prices = adapter.findPricesByBrandAndProduct(1L, 35455L, applicationDate);

        // Assert
        assertThat(prices).hasSize(1);
        Price price = prices.get(0);
        assertThat(price.getBrandId()).isEqualTo(1);
        assertThat(price.getProductId()).isEqualTo(35455);
        assertThat(price.getPriceList()).isEqualTo(2);
        assertThat(price.getPriority()).isEqualTo(1);
        assertThat(price.getFinalPrice()).isEqualByComparingTo(BigDecimal.valueOf(25.45));
        assertThat(price.getStartDate()).isEqualTo(LocalDateTime.of(2020, 6, 14, 15, 0));
        assertThat(price.getEndDate()).isEqualTo(LocalDateTime.of(2020, 6, 14, 18, 30));

        // Verify repository interaction
        verify(repository, times(1))
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        1, 35455, applicationDate, applicationDate);
    }

    @Test
    void findPricesByBrandAndProduct_WhenNoResults_ReturnsEmptyList() {
        // Arrange
        LocalDateTime applicationDate = LocalDateTime.now();
        when(repository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                anyInt(), anyInt(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of());

        // Act
        List<Price> prices = adapter.findPricesByBrandAndProduct(1L, 35455L, applicationDate);

        // Assert
        assertThat(prices).isEmpty();

        // Verify repository call
        verify(repository, times(1))
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        1, 35455, applicationDate, applicationDate);
    }
}
