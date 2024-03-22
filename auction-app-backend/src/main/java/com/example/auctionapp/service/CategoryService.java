package com.example.auctionapp.service;

import com.example.auctionapp.dto.request.CategoryRequestDTO;
import com.example.auctionapp.dto.response.CategoryDTO;
import com.example.auctionapp.entity.Category;
import com.example.auctionapp.entity.Category;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories
                .stream()
                .map(CategoryDTO::new)
                .collect(toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category with the given ID does not exist");
        }

        return new CategoryDTO(category.get());
    }

    public CategoryDTO addCategory(CategoryRequestDTO payload) {
        Category category = categoryRepository.save(payload.toEntity());
        return new CategoryDTO(category);
    }

    public CategoryDTO updateCategory(Long id, CategoryRequestDTO payload) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new RuntimeException("Category with the given ID does not exist");
        }

        Category updatedCategory = payload.toEntity();
        updatedCategory.setCategoryId(category.get().getCategoryId());
        updatedCategory = categoryRepository.save(updatedCategory);
        return new CategoryDTO(updatedCategory);
    }

    public void deleteCategory(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(categoryRepository::delete);
    }
}
