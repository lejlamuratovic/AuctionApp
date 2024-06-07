package com.example.auctionapp.specification;

import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.request.GetProductRequest;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProductSpecification {
    public static Specification<ProductEntity> withDynamicQuery(final UUID categoryId, final String searchProduct) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categoryId != null) {
                // get subcategories
                Subquery<UUID> subquery = query.subquery(UUID.class);
                Root<CategoryEntity> categoryRoot = subquery.from(CategoryEntity.class);

                subquery.select(categoryRoot.get("categoryId")).distinct(true);

                subquery.where(
                        criteriaBuilder.or(
                                criteriaBuilder.equal(categoryRoot.get("categoryId"), categoryId),
                                criteriaBuilder.equal(categoryRoot.get("parentCategory").get("categoryId"), categoryId)
                        )
                );

                predicates.add(root.join("categoryEntity").get("categoryId").in(subquery));
            }

            if (searchProduct != null && !searchProduct.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchProduct.toLowerCase() + "%"));
            }

            predicates.add(criteriaBuilder.equal(root.get("status"), ProductStatus.ACTIVE));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<ProductEntity> buildSpecification(final GetProductRequest getProductRequest) {
        return withDynamicQuery(getProductRequest.getCategoryId(), getProductRequest.getSearchProduct());
    }
}
