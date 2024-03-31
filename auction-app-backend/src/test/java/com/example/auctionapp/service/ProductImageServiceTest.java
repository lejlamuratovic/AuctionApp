package com.example.auctionapp.service;

import com.example.auctionapp.AuctionAppBackendApplication;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.ProductImageEntity;
import com.example.auctionapp.model.ProductImage;
import com.example.auctionapp.repository.ProductImageRepository;
import com.example.auctionapp.service.implementation.ProductImageServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuctionAppBackendApplication.class)
public class ProductImageServiceTest {

    @MockBean
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductImageServiceImpl productImageService;

    @Test
    public void whenGetImagesByProduct_thenReturnProductImagesList() {
        UUID productId = UUID.randomUUID();
        ProductEntity productEntity = new ProductEntity();

        productEntity.setProductId(productId);
        productEntity.setName("Test Product");
        productEntity.setDescription("Test Description");
        productEntity.setStartPrice(BigDecimal.valueOf(200));
        productEntity.setStartDate(LocalDateTime.now());
        productEntity.setEndDate(LocalDateTime.now().plusDays(1));
        productEntity.setImageUrl("http://example.com/product.jpg");
        productEntity.setStatus("ACTIVE");

        ProductImageEntity productImageEntity = new ProductImageEntity();

        productImageEntity.setImageId(UUID.randomUUID());
        productImageEntity.setImageUrl("http://example.com/image.jpg");
        productImageEntity.setProductEntity(productEntity);
        
        List<ProductImageEntity> productImageEntities = Arrays.asList(productImageEntity);

        when(productImageRepository.findByProductEntity_ProductId(productId)).thenReturn(productImageEntities);

        List<ProductImage> result = productImageService.getImagesByProduct(productId);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getImageUrl()).isEqualTo(productImageEntity.getImageUrl());
        assertThat(result.get(0).getProductId()).isEqualTo(productId);
    }
}
