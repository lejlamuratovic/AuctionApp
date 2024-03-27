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

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/topLevel")
    public List<Category> getTopLevelCategories() {
        return categoryService.getTopLevelCategories();
    }

    @PostMapping
    public Category addCategory(@RequestBody CategoryAddRequest category) {
        return categoryService.addCategory(category);
    }

    @GetMapping(path = "/{id}")
    public Category getCategoryById(@PathVariable UUID id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping(path = "/{id}")
    public Category updateCategory(@PathVariable UUID id, @RequestBody CategoryAddRequest category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
    }

    @GetMapping("/paginated")
    public Page<Category> getCategoriesPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size) {
        return categoryService.getCategoriesPaginated(page, size);
    }
}
