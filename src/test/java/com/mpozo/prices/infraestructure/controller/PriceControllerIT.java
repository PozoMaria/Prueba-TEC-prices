package com.mpozo.prices.infraestructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PriceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Escenario OK (control extra):
     * Devuelve el precio con mayor prioridad
     */
    @Test
    void getPrice_whenPriceExists_returns200() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T16:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.brandId").value(1))
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.finalPrice").value(25.45));
    }

    /**
     * Escenario 1:
     * Búsqueda sin resultados → 404 Not Found
     */
    @Test
    void getPrice_whenNoResults_returns404() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "99999")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }


    /**
     * Escenario 2:
     * Falta de parámetros obligatorios → 400 Bad Request
     */
    @Test
    void getPrice_whenMissingRequiredParam_returns400() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .string("Missing required parameter: productId"));
    }


    /**
     * Escenario 3a:
     * Fecha con formato incorrecto → 400 Bad Request
     */
    @Test
    void getPrice_whenInvalidDateFormat_returns400() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("brandId", "1")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14 10:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "Invalid value '2020-06-14 10:00' for parameter 'applicationDate'"
                ));
    }


    /**
     * Escenario 3b:
     * Parámetro numérico inválido → 400 Bad Request
     */
    @Test
    void getPrice_whenNonNumericId_returns400() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("brandId", "abc")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        "Invalid value 'abc' for parameter 'brandId'"
                ));
    }
}
