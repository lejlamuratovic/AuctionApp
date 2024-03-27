package com.example.auctionapp.model;

import com.example.auctionapp.entity.CategoryEntity;

import java.util.UUID;

public class Category {

    private UUID id;
    private String name;
    private Category parentCategory;

    public Category() {
    }

    public static Category toDomainModel(CategoryEntity categoryEntity) {
        Category category = new Category();
        category.setId(categoryEntity.getCategoryId());
        category.setName(categoryEntity.getName());
        if (categoryEntity.getParentCategory() != null) {
            category.setParentCategory(toDomainModel(categoryEntity.getParentCategory()));
        }
        return category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
