package com.example.auctionapp.request;

import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.util.builderpattern.GenericBuilder;

import java.util.UUID;

public class CategoryAddRequest {
    private String name;
    private UUID parentCategoryId;

    public CategoryAddRequest() {
    }

    public CategoryEntity toEntity() {
        return GenericBuilder.of(CategoryEntity::new)
                .with(CategoryEntity::setName, this.name)
                .build();
    }

    public CategoryAddRequest(final String name, final UUID parentCategoryId) {
        this.name = name;
        this.parentCategoryId = parentCategoryId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public UUID getParentCategoryId() {
        return this.parentCategoryId;
    }

    public void setParentCategoryId(final UUID parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
