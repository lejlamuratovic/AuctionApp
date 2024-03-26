package com.example.auctionapp.controller;

import com.example.auctionapp.dto.request.ProductRequestDTO;
import com.example.auctionapp.dto.response.CategoryDTO;
import com.example.auctionapp.dto.response.ProductDTO;
import com.example.auctionapp.service.ProductService;
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
    public List<ProductDTO> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public ProductDTO addProduct(@RequestBody ProductRequestDTO product) {
        return productService.addProduct(product);
    }

    @GetMapping(path = "/{id}")
    public ProductDTO getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PutMapping(path = "/{id}")
    public ProductDTO updateProduct(@PathVariable UUID id, @RequestBody ProductRequestDTO product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/paginated")
    public Page<ProductDTO> getProductsPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        return productService.getProductsPaginated(page, size);
    }

    @GetMapping("/newArrivals")
    public Page<ProductDTO> getNewArrivals(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        return productService.getNewArrivals(page, size);
    }

    @GetMapping("/lastChance")
    public Page<ProductDTO> getLastChance(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "8") int size) {
        return productService.getLastChance(page, size);
    }

    @GetMapping("/random")
    public ProductDTO getRandomProduct() {
        return productService.getRandomProduct();
    }
}
