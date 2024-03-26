package com.example.auctionapp.service;

import com.example.auctionapp.dto.request.CategoryRequestDTO;
import com.example.auctionapp.dto.response.CategoryDTO;
import com.example.auctionapp.entity.Category;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public CategoryDTO getCategoryById(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category with the given ID does not exist");
        }

        return new CategoryDTO(category.get());
    }

    public CategoryDTO addCategory(CategoryRequestDTO categoryRequest) {
        Category category = categoryRequest.toEntity();

        if (categoryRequest.getParentCategoryId() != null) {
            Category parentCategory = categoryRepository.findById(categoryRequest.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParentCategory(parentCategory);
        }

        Category newCategory = categoryRepository.save(category);
        return new CategoryDTO(newCategory);
    }

    public CategoryDTO updateCategory(UUID id, CategoryRequestDTO categoryRequest) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isEmpty()) {
            throw new RuntimeException("Category with the given ID does not exist");
        }

        Category updatedCategory = categoryRequest.toEntity();
        updatedCategory.setCategoryId(category.get().getCategoryId());
        updatedCategory = categoryRepository.save(updatedCategory);
        return new CategoryDTO(updatedCategory);
    }

    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        category.ifPresent(categoryRepository::delete);
    }

    // get exercises paginated
    public Page<CategoryDTO> getCategoriesPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.map(CategoryDTO::new);
    }

    public List<CategoryDTO> getTopLevelCategories() {
        List<Category> topCategories = categoryRepository.findByParentCategoryIsNull();

        return topCategories
                .stream()
                .map(CategoryDTO::new)
                .collect(toList());
    }
}
