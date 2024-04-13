package com.example.auctionapp.service.implementation;

import com.example.auctionapp.request.ProductAddRequest;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.response.ProductSearchResponse;
import com.example.auctionapp.service.ProductService;
import com.example.auctionapp.specification.ProductSpecification;
import com.example.auctionapp.util.LevenshteinDistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(final ProductRepository productRepository, final CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductSearchResponse getProducts(UUID categoryId, String searchProduct, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<ProductEntity> specification = ProductSpecification.withDynamicQuery(categoryId, searchProduct);

        Page<Product> products = productRepository.findAll(specification, pageable).map(ProductEntity::toDomainModel);
        String suggestedQuery = null;

        if (products.getTotalElements() < size && searchProduct != null && !searchProduct.isBlank()) {
            suggestedQuery = suggestCorrection(searchProduct);
            if (suggestedQuery != null && !suggestedQuery.equalsIgnoreCase(searchProduct)) {
                suggestedQuery = suggestedQuery;
            } else {
                suggestedQuery = null;
            }
        }

        return new ProductSearchResponse(products, suggestedQuery);
    }

    private String suggestCorrection(String query) {
        List<String> productNames = this.productRepository.findAllProductNames();
        final int DISTANCE_THRESHOLD = 2;
        Map<String, Integer> combinationFrequency = new HashMap<>();

        // user query into words
        String[] queryWords = query.toLowerCase().split("\\W+");

        for (String productName : productNames) {
            String[] productWords = productName.toLowerCase().split("\\W+");

            // to generate all possible combinations of product words that match length of query words
            for (int i = 0; i <= productWords.length - queryWords.length; i++) {
                StringBuilder sb = new StringBuilder();
                boolean isCloseMatch = true;

                for (int j = 0; j < queryWords.length; j++) {
                    int distance = LevenshteinDistance.computeLevenshteinDistance(queryWords[j], productWords[i + j]);
                    if (distance > DISTANCE_THRESHOLD) {
                        isCloseMatch = false;
                        break;
                    }
                    sb.append(productWords[i + j]).append(" ");
                }

                if (isCloseMatch) {
                    String matchedPhrase = sb.toString().trim();
                    combinationFrequency.put(matchedPhrase, combinationFrequency.getOrDefault(matchedPhrase, 0) + 1);
                }
            }
        }

        if (combinationFrequency.isEmpty()) {
            return null;
        }

        // return combination with highest frequency
        return Collections.max(combinationFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    @Override
    public Product getProductById(final UUID id) {
        final ProductEntity productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));

        return productEntity.toDomainModel();
    }

    @Override
    public Product addProduct(final ProductAddRequest productRequest) {
        final ProductEntity productEntity = productRequest.toEntity();

        if (productRequest.getCategoryId() != null) {
            productEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist")));
        }

        return this.productRepository.save(productEntity).toDomainModel();
    }

    @Override
    public Product updateProduct(final UUID id, final ProductAddRequest productRequest) {
        final ProductEntity existingProductEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));

        existingProductEntity.setName(productRequest.getName());
        existingProductEntity.setDescription(productRequest.getDescription());
        existingProductEntity.setStartPrice(productRequest.getStartPrice());
        existingProductEntity.setStartDate(productRequest.getStartDate());
        existingProductEntity.setEndDate(productRequest.getEndDate());
        existingProductEntity.setStatus(productRequest.getStatus());

        if (productRequest.getCategoryId() != null) {
            existingProductEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        }

        return this.productRepository.save(existingProductEntity).toDomainModel();
    }

    @Override
    public void deleteProduct(final UUID id) {
        final ProductEntity productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        this.productRepository.delete(productEntity);
    }

    public Page<Product> getProductsByCriteria(final int page, final int size, final String type) {
        Sort.Direction direction = Sort.Direction.DESC;
        String sortBy = "startDate";

        if ("lastChance".equals(type)) {
            direction = Sort.Direction.ASC;
            sortBy = "endDate";
        }

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(Sort.Direction.ASC, "productId"));
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable).map(ProductEntity::toDomainModel);
    }

    @Override
    public Product getRandomProduct() {
        final Optional<ProductEntity> randomProductEntity = productRepository.findRandomProduct();

        if (randomProductEntity.isEmpty()) {
            throw new ResourceNotFoundException("No products available");
        }

        return randomProductEntity.get().toDomainModel();
    }
}

