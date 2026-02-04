package com.mpozo.prices.application.service.impl;

import com.mpozo.prices.domain.entity.PriceEntity;
import com.mpozo.prices.domain.mapper.PriceMapper;
import com.mpozo.prices.domain.model.Price;
import com.mpozo.prices.domain.repository.PriceEntityRepository;
import com.mpozo.prices.infraestructure.dto.PriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {

    @Mock
    private PriceEntityRepository priceRepository;

    @Mock
    private PriceMapper priceMapper;

    @InjectMocks
    private PriceServiceImpl priceService;

    private PriceEntity priceEntity1;
    private PriceEntity priceEntity2;

    private Price price1;
    private Price price2;

    @BeforeEach
    void setUp() {
        priceEntity1 = new PriceEntity();
        priceEntity1.setProductId(35455);
        priceEntity1.setBrandId(1);
        priceEntity1.setPriceList(1);
        priceEntity1.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
        priceEntity1.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
        priceEntity1.setPriority(0);
        priceEntity1.setPrice(BigDecimal.valueOf(35.50));


        priceEntity2 = new PriceEntity();
        priceEntity2.setProductId(35455);
        priceEntity2.setBrandId(1);
        priceEntity2.setPriceList(2);
        priceEntity2.setStartDate(LocalDateTime.of(2020, 6, 14, 15, 0));
        priceEntity2.setEndDate(LocalDateTime.of(2020, 6, 14, 18, 30));
        priceEntity2.setPriority(1);
        priceEntity2.setPrice(BigDecimal.valueOf(25.45));

        price1 = new Price(
                35455,
                1,
                1,
                priceEntity1.getStartDate(),
                priceEntity1.getEndDate(),
                0,
                BigDecimal.valueOf(35.50)
        );

        price2 = new Price(
                35455,
                1,
                2,
                priceEntity2.getStartDate(),
                priceEntity2.getEndDate(),
                1,
                BigDecimal.valueOf(25.45)
        );
    }

    @Test
    void getPrice_ReturnsPriceWithHighestPriority() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        when(priceRepository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        anyInt(), anyInt(), any(), any()))
                .thenReturn(List.of(priceEntity1, priceEntity2));

        when(priceMapper.toDomain(priceEntity1)).thenReturn(price1);
        when(priceMapper.toDomain(priceEntity2)).thenReturn(price2);

        PriceResponse response = priceService.getPrice(1L, 35455L, applicationDate);

        assertNotNull(response);
        assertEquals(2, response.getPriceList());
        assertEquals(BigDecimal.valueOf(25.45), response.getFinalPrice());
        assertEquals(1, response.getBrandId());
        assertEquals(35455, response.getProductId());
    }

    @Test
    void getPrice_WhenNoPriceFound_ThrowsException() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        when(priceRepository
                .findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                        anyInt(), anyInt(), any(), any()))
                .thenReturn(List.of());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> priceService.getPrice(1L, 35455L, applicationDate)
        );

        assertEquals("No price found", exception.getMessage());
    }
}
