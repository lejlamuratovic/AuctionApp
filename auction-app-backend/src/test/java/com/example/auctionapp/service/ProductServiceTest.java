package com.example.auctionapp.service;

import com.example.auctionapp.AuctionAppBackendApplication;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.ProductImageEntity;
import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(classes = AuctionAppBackendApplication.class)
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void whenGetLastChance_thenReturnProductsSortedByEndDateAsc() {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategoryId(UUID.randomUUID());
        categoryEntity.setName("Sneakers");

        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(UUID.randomUUID());
        userEntity.setEmail("test@example.com");
        userEntity.setFirstName("test");
        userEntity.setLastName("example");
        userEntity.setPassword("testexample");

        ProductEntity productEntity = new ProductEntity();

        productEntity.setProductId(UUID.randomUUID());
        productEntity.setName("Limited Edition Sneakers");
        productEntity.setDescription("Limited time offer");
        productEntity.setStartPrice(BigDecimal.valueOf(200));
        productEntity.setStartDate(LocalDateTime.now());
        productEntity.setEndDate(LocalDateTime.now().plusDays(1));
        productEntity.setStatus(ProductStatus.ACTIVE);
        productEntity.setCategory(categoryEntity);
        productEntity.setUserEntity(userEntity);

        ProductImageEntity productImage = new ProductImageEntity();

        productImage.setImageId(UUID.randomUUID());
        productImage.setImageUrl("http://example.com/image.jpg");
        productImage.setProductEntity(productEntity);

        productEntity.setProductImages(List.of(productImage));

        Page<ProductEntity> pageOfProductEntities = new PageImpl<>(List.of(productEntity));

        when(productRepository.findProductEntitiesByStatusEquals(any(Pageable.class), eq(ProductStatus.ACTIVE)))
                .thenReturn(pageOfProductEntities);

        Page<Product> resultPage = productService.getProductsByCriteria(0, 1, "lastChance");

        assertThat(resultPage.getContent()).hasSize(1);
        
        Product resultProduct = resultPage.getContent().get(0);

        assertThat(resultProduct.getName()).isEqualTo(productEntity.getName());
        assertThat(resultProduct.getDescription()).isEqualTo(productEntity.getDescription());
        assertThat(resultProduct.getProductImages()).isNotEmpty();
    }
}
