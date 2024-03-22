package com.example.auctionapp.dto.response;

import com.example.auctionapp.entity.Category;

public class CategoryDTO {

    private Long id;
    private String name;

    public CategoryDTO(Category category) {
        this.id = category.getCategoryId();
        this.name = category.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
