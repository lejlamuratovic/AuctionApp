package com.example.auctionapp.repository;

import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {
    Page<ProductEntity> findProductEntitiesByStatusEquals(final Pageable pageable, final ProductStatus productStatus);

    @Query(value = "SELECT p FROM ProductEntity p ORDER BY RANDOM() LIMIT 1")
    Optional<ProductEntity> findRandomProduct();

    @Query("SELECT DISTINCT p.name FROM ProductEntity p")
    List<String> findAllProductNames();

    Optional<ProductEntity> findProductEntityByProductId(final UUID productId);

    Page<ProductEntity> findProductEntityByUserEntity_UserIdAndAndStatus(final UUID userId,
                                                                         final ProductStatus status,
                                                                         Pageable page);

    List<ProductEntity> findProductEntityByUserEntity_UserIdAndAndStatusAndBidsCountIsGreaterThan(final UUID userId,
                                                                                                  final ProductStatus status,
                                                                                                  final int bidsCount);

    @Modifying
    @Query("UPDATE ProductEntity p SET p.status = 'INACTIVE' WHERE p.status = 'ACTIVE' AND p.endDate <= :now")
    int updateProductStatus(final LocalDateTime now);

    List<ProductEntity> findProductEntitiesByUserEntity_UserIdOrderByStartDateDesc(final UUID userId);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.categoryEntity.categoryId IN " +
            "(SELECT c.categoryId FROM CategoryEntity c " +
            "WHERE c.parentCategory.categoryId = :categoryId OR c.categoryId = :categoryId) AND p.status = 'ACTIVE' " +
            "ORDER BY p.bidsCount DESC, p.endDate ASC")
    List<ProductEntity> findTopPopularProductEntitiesByCategoryId(final UUID categoryId, final Pageable pageable);

    @Query(value = "SELECT p FROM ProductEntity p WHERE p.status = 'ACTIVE' ORDER BY p.bidsCount DESC")
    List<ProductEntity> findMostPopularProducts(final Pageable page);
    
    void deleteAllByUserEntity_UserIdAndStatus(final UUID userId, final ProductStatus status);

    @Query("SELECT COALESCE(MIN(p.startPrice), 0), COALESCE(MAX(p.startPrice), 0) " +
            "FROM ProductEntity p WHERE p.status = 'ACTIVE'")
    List<BigDecimal[]> findMinAndMaxPrices();
}
