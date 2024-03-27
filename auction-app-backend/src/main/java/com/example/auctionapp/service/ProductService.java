package com.example.auctionapp.service;

import com.example.auctionapp.model.Product;
import com.example.auctionapp.request.ProductAddRequest;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProducts();
    Product getProductById(UUID id);
    Product addProduct(ProductAddRequest productRequest);
    Product updateProduct(UUID id, ProductAddRequest productRequest);
    void deleteProduct(UUID id);
    Page<Product> getProductsPaginated(int page, int size);
    Page<Product> getNewArrivals(int page, int size);
    Page<Product> getLastChance(int page, int size);
    Product getRandomProduct();
}
