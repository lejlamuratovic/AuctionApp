package com.example.auctionapp.controller;

import com.example.auctionapp.model.Product;
import com.example.auctionapp.service.ProductService;
import com.example.auctionapp.request.ProductAddRequest;
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
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return this.productService.getProducts();
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductAddRequest product) {
        return this.productService.addProduct(product);
    }

    @GetMapping(path = "/{id}")
    public Product getProductById(@PathVariable UUID id) {
        return this.productService.getProductById(id);
    }

    @PutMapping(path = "/{id}")
    public Product updateProduct(@PathVariable UUID id, @RequestBody ProductAddRequest product) {
        return this.productService.updateProduct(id, product);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        this.productService.deleteProduct(id);
    }

    @GetMapping("/paginated")
    public Page<Product> getProductsPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        return this.productService.getProductsPaginated(page, size);
    }

    @GetMapping("/newArrivals")
    public Page<Product> getNewArrivals(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        return this.productService.getNewArrivals(page, size);
    }

    @GetMapping("/lastChance")
    public Page<Product> getLastChance(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        return this.productService.getLastChance(page, size);
    }

    @GetMapping("/random")
    public Product getRandomProduct() {
        return this.productService.getRandomProduct();
    }
}
