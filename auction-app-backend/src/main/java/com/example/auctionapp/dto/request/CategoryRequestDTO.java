package com.example.auctionapp.dto.request;

import com.example.auctionapp.entity.Category;

public class CategoryRequestDTO {

    private String name;

    public CategoryRequestDTO() {
    }

    public CategoryRequestDTO(Category category) {
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
}
