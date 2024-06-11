package com.example.auctionapp.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class GetProductRequest {
    private UUID categoryId;
    private String searchProduct;
    private String sortField = "name";
    private String sortDirection = "ASC";
    private int page = 0;
    private int size = 8;
    private List<UUID> subcategoryIds;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    public GetProductRequest() {
    }

    public GetProductRequest(final UUID categoryId,
                             final String searchProduct,
                             final String sortField,
                             final String sortDirection,
                             final int page,
                             final int size,
                             final List<UUID> subcategoryIds,
                             final BigDecimal minPrice,
                             final BigDecimal maxPrice
    ) {
        this.categoryId = categoryId;
        this.searchProduct = searchProduct;
        this.sortField = sortField;
        this.sortDirection = sortDirection;
        this.page = page;
        this.size = size;
        this.subcategoryIds = subcategoryIds;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public UUID getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(final UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getSearchProduct() {
        return this.searchProduct;
    }

    public void setSearchProduct(final String searchProduct) {
        this.searchProduct = searchProduct;
    }

    public String getSortField() {
        return this.sortField;
    }

    public void setSortField(final String sortField) {
        this.sortField = sortField;
    }

    public String getSortDirection() {
        return this.sortDirection;
    }

    public void setSortDirection(final String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(final int page) {
        this.page = page;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public List<UUID> getSubcategoryIds() {
        return this.subcategoryIds;
    }

    public void setSubcategoryIds(final List<UUID> subcategoryIds) {
        this.subcategoryIds = subcategoryIds;
    }

    public BigDecimal getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(final BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(final BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
