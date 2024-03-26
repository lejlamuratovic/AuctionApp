package com.example.auctionapp.dto.request;

import com.example.auctionapp.entity.Category;

public class CategoryRequestDTO {

    private String name;
    private Long parentCategoryId;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(Category category, Long parentCategory) {
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

    public Long getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Long parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
