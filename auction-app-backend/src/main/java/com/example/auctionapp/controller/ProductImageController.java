package com.example.auctionapp.controller;

import com.example.auctionapp.model.ProductImage;
import com.example.auctionapp.service.ProductImageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/product-image")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping("/{id}")
    public List<ProductImage> getImagesByProduct(@PathVariable UUID id) {
        return this.productImageService.getImagesByProduct(id);
    }
}
