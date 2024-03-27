package com.example.auctionapp.service;

import com.example.auctionapp.request.ProductAddRequest;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
import com.example.auctionapp.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(Product::toDomainModel)
                .collect(toList());
    }

    public Product getProductById(UUID id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));
        return Product.toDomainModel(productEntity);
    }

    public Product addProduct(ProductAddRequest productRequest) {
        ProductEntity productEntity = productRequest.toEntity();
        if (productRequest.getCategoryId() != null) {
            productEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist")));
        }
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        return Product.toDomainModel(savedProductEntity);
    }

    public Product updateProduct(UUID id, ProductAddRequest productRequest) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));
        productEntity.setName(productRequest.getName());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setStartPrice(productRequest.getStartPrice());
        productEntity.setStartDate(productRequest.getStartDate());
        productEntity.setEndDate(productRequest.getEndDate());
        productEntity.setImageUrl(productRequest.getImageUrl());
        productEntity.setStatus(productRequest.getStatus());
        if (productRequest.getCategoryId() != null) {
            productEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        }
        ProductEntity updatedProductEntity = productRepository.save(productEntity);
        return Product.toDomainModel(updatedProductEntity);
    }

    public void deleteProduct(UUID id) {
        ProductEntity productEntity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(productEntity);
    }

    public Page<Product> getProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        return productPage.map(Product::toDomainModel);
    }

    public Page<Product> getNewArrivals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startDate"));
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        return productPage.map(Product::toDomainModel);
    }

    public Page<Product> getLastChance(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "endDate"));
        Page<ProductEntity> productPage = productRepository.findAll(pageable);
        return productPage.map(Product::toDomainModel);
    }

    public Product getRandomProduct() {
        List<UUID> productIds = productRepository.findProductIds();
        if (productIds.isEmpty()) {
            throw new ResourceNotFoundException("No products available");
        }
        Random random = new Random();
        UUID randomProductId = productIds.get(random.nextInt(productIds.size()));
        return getProductById(randomProductId);
    }
}
