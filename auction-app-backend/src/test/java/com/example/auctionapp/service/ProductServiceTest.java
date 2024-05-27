package com.example.auctionapp.service;

import com.example.auctionapp.AuctionAppBackendApplication;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.ProductImageEntity;
import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.response.ProductSearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Test
    public void whenGetSortedProductsByName_thenReturnSortedProducts() {
        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategoryId(UUID.randomUUID());
        categoryEntity.setName("Electronics");

        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(UUID.randomUUID());
        userEntity.setEmail("user@example.com");
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setPassword("password123");

        ProductEntity productA = new ProductEntity();

        productA.setProductId(UUID.randomUUID());
        productA.setName("A - Product");
        productA.setDescription("Product A Description");
        productA.setStartPrice(BigDecimal.valueOf(300));
        productA.setStartDate(LocalDateTime.now());
        productA.setEndDate(LocalDateTime.now().plusDays(2));
        productA.setStatus(ProductStatus.ACTIVE);
        productA.setCategory(categoryEntity);
        productA.setUserEntity(userEntity);

        ProductEntity productB = new ProductEntity();

        productB.setProductId(UUID.randomUUID());
        productB.setName("B - Product");
        productB.setDescription("Product B Description");
        productB.setStartPrice(BigDecimal.valueOf(150));
        productB.setStartDate(LocalDateTime.now().minusDays(1));
        productB.setEndDate(LocalDateTime.now().plusDays(1));
        productB.setStatus(ProductStatus.ACTIVE);
        productB.setCategory(categoryEntity);
        productB.setUserEntity(userEntity);

        List<ProductEntity> sortedProducts = List.of(productA, productB);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(sortedProducts));

        UUID categoryId = categoryEntity.getCategoryId();
        ProductSearchResponse response = productService.getProducts(categoryId,
                                                                    "", 
                                                                    "name", 
                                                                    "ASC", 
                                                                    0, 
                                                                    10);

        List<Product> results = response.getProducts().getContent();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getName()).isEqualTo("A - Product");
        assertThat(results.get(1).getName()).isEqualTo("B - Product");
    }

    @Test
    public void whenGetLatestProducts_thenReturnProductsSortedByStartDateDesc() {
        UserEntity userEntity = new UserEntity();

        userEntity.setUserId(UUID.randomUUID());
        userEntity.setEmail("user@example.com");
        userEntity.setFirstName("John");
        userEntity.setLastName("Doe");
        userEntity.setPassword("password123");

        UUID categoryId = UUID.randomUUID();

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setCategoryId(categoryId);
        categoryEntity.setName("Category");

        ProductEntity recentProduct = new ProductEntity();

        recentProduct.setProductId(UUID.randomUUID());
        recentProduct.setName("Recent Product");
        recentProduct.setDescription("Just launched");
        recentProduct.setStartPrice(BigDecimal.valueOf(500));
        recentProduct.setStartDate(LocalDateTime.now());
        recentProduct.setStatus(ProductStatus.ACTIVE);
        recentProduct.setCategory(categoryEntity);
        recentProduct.setUserEntity(userEntity);

        ProductEntity olderProduct = new ProductEntity();

        olderProduct.setProductId(UUID.randomUUID());
        olderProduct.setName("Older Product");
        olderProduct.setDescription("Launched last week");
        olderProduct.setStartPrice(BigDecimal.valueOf(350));
        olderProduct.setStartDate(LocalDateTime.now().minusWeeks(1));
        olderProduct.setStatus(ProductStatus.ACTIVE);
        olderProduct.setCategory(categoryEntity);
        olderProduct.setUserEntity(userEntity);

        List<ProductEntity> sortedProducts = Arrays.asList(recentProduct, olderProduct);

        Page<ProductEntity> productPage = new PageImpl<>(sortedProducts);

        when(productRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(productPage);

        ProductSearchResponse response = productService.getProducts(categoryId, 
                                                                    "", 
                                                                    "startDate", 
                                                                    "DESC", 
                                                                    0, 
                                                                    10);

        List<Product> results = response.getProducts().getContent();

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getName()).isEqualTo("Recent Product");
        assertThat(results.get(1).getName()).isEqualTo("Older Product");
    }
}
