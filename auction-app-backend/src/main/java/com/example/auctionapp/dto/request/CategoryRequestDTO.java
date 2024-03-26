package com.example.auctionapp.dto.request;

import com.example.auctionapp.entity.Category;

import java.util.UUID;

public class CategoryRequestDTO {

    private String name;
    private UUID parentCategoryId;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(Category category, UUID parentCategory) {
        this.name = category.getName();
    }

    public Category toEntity() {
        Category category = new Category();

        category.setName(name);
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(UUID parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
