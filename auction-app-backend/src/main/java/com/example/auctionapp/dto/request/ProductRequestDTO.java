package com.example.auctionapp.dto.request;

import com.example.auctionapp.dto.response.CategoryDTO;
import com.example.auctionapp.entity.Category;
import com.example.auctionapp.entity.Product;

import java.math.BigDecimal;
import java.util.Date;

public class ProductRequestDTO {

    private String name;
    private String description;
    private BigDecimal startPrice;
    private Date startDate;
    private Date endDate;
    private String imageUrl;
    private String status;
    private Long categoryId;

    public ProductRequestDTO() {
    }

    public ProductRequestDTO(Product product) {
        this.name = product.getName();
        this.description = product.getDescription();
        this.startPrice = product.getStartPrice();
        this.startDate = product.getStartDate();
        this.endDate = product.getEndDate();
        this.imageUrl = product.getImageUrl();
        this.status = product.getStatus();
    }

    public Product toEntity() {
        Product product = new Product();

        product.setName(name);
        product.setDescription(description);
        product.setStartPrice(startPrice);
        product.setStartDate(startDate);
        product.setEndDate(endDate);
        product.setImageUrl(imageUrl);
        product.setStatus(status);

        return product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
