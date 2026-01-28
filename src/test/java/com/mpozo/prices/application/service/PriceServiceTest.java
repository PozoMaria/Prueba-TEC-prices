package com.mpozo.prices.application.service;

import com.mpozo.prices.application.dto.PriceResponse;
import com.mpozo.prices.application.port.out.LoadPricePort;
import com.mpozo.prices.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private LoadPricePort loadPricePort;
    private PriceService priceService;

    private Price price1;
    private Price price2;

    @BeforeEach
    void setUp() {
        loadPricePort = Mockito.mock(LoadPricePort.class);
        priceService = new PriceService(loadPricePort);

        // Creamos algunos precios de ejemplo
        price1 = new Price(
                35455,          // productId
                1,              // brandId
                1,               // priceList
                LocalDateTime.of(2020, 6, 14, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 59),
                0,// priority
                BigDecimal.valueOf(35.50)
        );

        price2 = new Price(
                35455,
                1,
                2,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                1,
                BigDecimal.valueOf(25.45)
        );
    }

    @Test
    void getPrice_ReturnsPriceWithHighestPriority() {
        // simulamos que LoadPricePort devuelve ambos precios
        when(loadPricePort.findPricesByBrandAndProduct(anyLong(), anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of(price1, price2));

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        PriceResponse response = priceService.getPrice(1L, 35455L, applicationDate);

        // Debe devolver price2 porque tiene mayor priority
        assertNotNull(response);
        assertEquals(2, response.getPriceList());
        assertEquals(BigDecimal.valueOf(25.45), response.getFinalPrice());
        assertEquals(1, response.getBrandId());
        assertEquals(35455, response.getProductId());
    }

    @Test
    void getPrice_WhenNoPriceFound_ThrowsException() {
        // simulamos que LoadPricePort devuelve lista vacía
        when(loadPricePort.findPricesByBrandAndProduct(anyLong(), anyLong(), any(LocalDateTime.class)))
                .thenReturn(List.of());

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> priceService.getPrice(1L, 35455L, applicationDate));

        assertEquals("No price found", exception.getMessage());
    }
}
