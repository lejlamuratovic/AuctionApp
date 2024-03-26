package com.example.auctionapp.service;

import com.example.auctionapp.dto.request.ProductRequestDTO;
import com.example.auctionapp.dto.response.ProductDTO;
import com.example.auctionapp.entity.Category;
import com.example.auctionapp.entity.Product;
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

    public List<ProductDTO> getProducts() {
        List<Product> products = productRepository.findAll();

        return products
                .stream()
                .map(ProductDTO::new)
                .collect(toList());
    }

    public ProductDTO getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product with the given ID does not exist");
        }

        return new ProductDTO(product.get());
    }

    public ProductDTO addProduct(ProductRequestDTO productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist"));

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setStartPrice(productRequest.getStartPrice());
        product.setStartDate(productRequest.getStartDate());
        product.setEndDate(productRequest.getEndDate());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStatus(productRequest.getStatus());
        product.setCategory(category);

        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    public ProductDTO updateProduct(UUID id, ProductRequestDTO productRequest) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist"));

        existingProduct.setName(productRequest.getName());
        existingProduct.setDescription(productRequest.getDescription());
        existingProduct.setStartPrice(productRequest.getStartPrice());
        existingProduct.setStartDate(productRequest.getStartDate());
        existingProduct.setEndDate(productRequest.getEndDate());
        existingProduct.setImageUrl(productRequest.getImageUrl());
        existingProduct.setStatus(productRequest.getStatus());
        existingProduct.setCategory(category);

        existingProduct = productRepository.save(existingProduct);
        return new ProductDTO(existingProduct);
    }

    public void deleteProduct(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
    }

    // get products paginated
    public Page<ProductDTO> getProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(ProductDTO::new);
    }

    // for new arrivals (startDate descending)
    public Page<ProductDTO> getNewArrivals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "startDate"));
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductDTO::new);
    }

    // for last chance (endDate ascending)
    public Page<ProductDTO> getLastChance(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "endDate"));
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(ProductDTO::new);
    }

    public ProductDTO getRandomProduct() {
        List<UUID> productIds = productRepository.findProductIds();

        if (productIds.isEmpty()) {
            throw new ResourceNotFoundException("No products available");
        }

        Random random = new Random();
        UUID randomProductId = productIds.get(random.nextInt(productIds.size()));
        return getProductById(randomProductId);
    }
}
