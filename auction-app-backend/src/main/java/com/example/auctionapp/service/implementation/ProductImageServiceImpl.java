package com.example.auctionapp.service.implementation;

import com.example.auctionapp.model.ProductImage;
import com.example.auctionapp.entity.ProductImageEntity;
import com.example.auctionapp.service.ProductImageService;
import com.example.auctionapp.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public List<ProductImage> getImagesByProduct(UUID productId) {
        return this.productImageRepository.findByProductEntity_ProductId(productId)
                .stream()
                .map(ProductImageEntity::toDomainModel)
                .collect(toList());
    }
}
