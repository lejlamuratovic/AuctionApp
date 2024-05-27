package com.example.auctionapp.controller;

import com.example.auctionapp.AuctionAppBackendApplication;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = AuctionAppBackendApplication.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void whenGetProductById_thenReturnProduct() throws Exception {
        UUID productId = UUID.randomUUID();
        Product product = new Product();

        product.setId(productId);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setStartPrice(BigDecimal.valueOf(200));
        product.setStartDate(LocalDateTime.now());
        product.setEndDate(LocalDateTime.now().plusDays(1));
        product.setStatus(ProductStatus.ACTIVE);

        Mockito.when(productService.getProductById(productId)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + productId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name").value("Test Product"))
                        .andExpect(jsonPath("$.description").value("Test Description"))
                        .andExpect(jsonPath("$.startPrice").value(200))
                        .andExpect(jsonPath("$.status").value("ACTIVE"));
    }
}
