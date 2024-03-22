package com.example.auctionapp.dto.response;

import com.example.auctionapp.entity.Category;

public class CategoryDTO {

    private String name;

    public CategoryDTO(Category category) {
        this.name = category.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
