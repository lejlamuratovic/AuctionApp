package com.example.auctionapp.service;

import com.example.auctionapp.model.ProductImage;

import java.util.List;
import java.util.UUID;

public interface ProductImageService {
    List<ProductImage> getImagesByProduct(UUID productId);
}
