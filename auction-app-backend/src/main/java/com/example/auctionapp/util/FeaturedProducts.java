package com.example.auctionapp.util;

import com.example.auctionapp.entity.BidEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.repository.BidRepository;
import com.example.auctionapp.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

public class FeaturedProducts {
    public static List<Product> getFeaturedProducts(final UUID userId,
                                                    final ProductRepository productRepository,
                                                    final BidRepository bidRepository) {
        final List<ProductEntity> seller = productRepository.findProductEntitiesByUserEntity_UserId(userId);
        final List<BidEntity> bids = bidRepository.findBidEntitiesByUserEntity_UserId(userId);

        return null;
    }
}