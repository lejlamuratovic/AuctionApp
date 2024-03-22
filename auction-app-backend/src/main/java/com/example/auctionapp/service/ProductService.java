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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product with the given ID does not exist");
        }

        return new ProductDTO(product.get());
    }

    public ProductDTO addProduct(ProductRequestDTO payload) {
        Category category = categoryRepository.findById(payload.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist"));

        Product product = new Product();
        product.setName(payload.getName());
        product.setDescription(payload.getDescription());
        product.setStartPrice(payload.getStartPrice());
        product.setStartDate(payload.getStartDate());
        product.setEndDate(payload.getEndDate());
        product.setImageUrl(payload.getImageUrl());
        product.setStatus(payload.getStatus());
        product.setCategory(category);

        product = productRepository.save(product);
        return new ProductDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductRequestDTO payload) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));

        Category category = categoryRepository.findById(payload.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist"));

        existingProduct.setName(payload.getName());
        existingProduct.setDescription(payload.getDescription());
        existingProduct.setStartPrice(payload.getStartPrice());
        existingProduct.setStartDate(payload.getStartDate());
        existingProduct.setEndDate(payload.getEndDate());
        existingProduct.setImageUrl(payload.getImageUrl());
        existingProduct.setStatus(payload.getStatus());
        existingProduct.setCategory(category);

        existingProduct = productRepository.save(existingProduct);
        return new ProductDTO(existingProduct);
    }

    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
    }

    // get products paginated
    public Page<ProductDTO> getProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);

        return productPage.map(ProductDTO::new);
    }
}
