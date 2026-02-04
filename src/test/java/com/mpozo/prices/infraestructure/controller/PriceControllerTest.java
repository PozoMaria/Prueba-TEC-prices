package com.mpozo.prices.infraestructure.controller;

import com.mpozo.prices.application.service.PriceService;
import com.mpozo.prices.infraestructure.dto.PriceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    private PriceResponse priceResponse;

    @BeforeEach
    void setUp() {
        // Creamos un PriceResponse de ejemplo
        priceResponse = new PriceResponse(
                35455,   // productId
                1,       // brandId
                2,       // priceList
                LocalDateTime.of(2020, 6, 14, 15, 0, 0), // startDate
                LocalDateTime.of(2020, 6, 14, 18, 30, 0), // endDate
                new BigDecimal("25.45")    // finalPrice
        );
    }

    @Test
    void getPrice_ReturnsPriceResponse() throws Exception {
        // Mock useCase
        Mockito.when(priceService.getPrice(
                eq(1L),
                eq(35455L),
                any(LocalDateTime.class)
        )).thenReturn(priceResponse);

        // call endpoint and verify response
        mockMvc.perform(get("/prices/getPrice")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.startDate").value("2020-06-14T15:00:00"))
                .andExpect(jsonPath("$.endDate").value("2020-06-14T18:30:00"))
                .andExpect(jsonPath("$.finalPrice").value(25.45));
    }

    @Test
    void getPrice_UseCaseThrowsException_Returns204() throws Exception {
        // Simula que el UseCase no encuentra precio
        Mockito.when(priceService.getPrice(any(), any(), any()))
                .thenThrow(new RuntimeException("No price found"));

        mockMvc.perform(get("/prices/getPrice")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent()); // espera 204 en vez de 5xx
    }
}
