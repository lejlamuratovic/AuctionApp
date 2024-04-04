package com.example.auctionapp.controller;

import com.example.auctionapp.model.Category;
import com.example.auctionapp.service.CategoryService;
import com.example.auctionapp.request.CategoryAddRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories() {
        return this.categoryService.getCategories();
    }

    @GetMapping("/top-level")
    public List<Category> getTopLevelCategories() {
        return this.categoryService.getTopLevelCategories();
    }

    @GetMapping("/with-subcategories")
    public List<Category> getCategoriesWithSubcategories() {
        return this.categoryService.getCategoriesWithSubcategories();
    }

    @PostMapping
    public Category addCategory(@RequestBody final CategoryAddRequest category) {
        return this.categoryService.addCategory(category);
    }

    @GetMapping(path = "/{id}")
    public Category getCategoryById(@PathVariable final UUID id) {
        return this.categoryService.getCategoryById(id);
    }

    @PutMapping(path = "/{id}")
    public Category updateCategory(@PathVariable final UUID id, @RequestBody final CategoryAddRequest category) {
        return this.categoryService.updateCategory(id, category);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCategory(@PathVariable final UUID id) {
        this.categoryService.deleteCategory(id);
    }
}
