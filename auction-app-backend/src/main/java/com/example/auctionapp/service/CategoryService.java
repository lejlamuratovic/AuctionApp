package com.example.auctionapp.service;

import com.example.auctionapp.request.CategoryAddRequest;
import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.model.Category;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::toDomainModel)
                .collect(toList());
    }

    public Category getCategoryById(UUID id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist"));
        return Category.toDomainModel(categoryEntity);
    }

    public Category addCategory(CategoryAddRequest categoryRequest) {
        CategoryEntity categoryEntity = categoryRequest.toEntity();

        if (categoryRequest.getParentCategoryId() != null) {
            CategoryEntity parentCategoryEntity = categoryRepository.findById(categoryRequest.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            categoryEntity.setParentCategory(parentCategoryEntity);
        }

        CategoryEntity newCategoryEntity = categoryRepository.save(categoryEntity);
        return Category.toDomainModel(newCategoryEntity);
    }

    public Category updateCategory(UUID id, CategoryAddRequest categoryRequest) {
        CategoryEntity existingCategoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist"));

        existingCategoryEntity.setName(categoryRequest.getName());
        CategoryEntity updatedCategoryEntity = categoryRepository.save(existingCategoryEntity);
        return Category.toDomainModel(updatedCategoryEntity);
    }

    public void deleteCategory(UUID id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(categoryEntity);
    }

    public Page<Category> getCategoriesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable)
                .map(Category::toDomainModel);
    }

    public List<Category> getTopLevelCategories() {
        return categoryRepository.findByParentCategoryIsNull()
                .stream()
                .map(Category::toDomainModel)
                .collect(toList());
    }
}
