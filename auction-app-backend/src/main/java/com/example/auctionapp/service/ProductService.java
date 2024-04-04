package com.example.auctionapp.service;

import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.request.ProductAddRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProductService {
    Page<Product> getProducts(final UUID categoryId, final int page, final int size);
    Product getProductById(final UUID id);
    Product addProduct(final ProductAddRequest productRequest);
    Product updateProduct(final UUID id, final ProductAddRequest productRequest);
    void deleteProduct(final UUID id);
    Page<Product> getProductsByCriteria(final int page, final int size, final String type);
    Product getRandomProduct();
    Page<Product> findProducts(final String searchQuery, final UUID categoryId, final int page, final int size);
}
