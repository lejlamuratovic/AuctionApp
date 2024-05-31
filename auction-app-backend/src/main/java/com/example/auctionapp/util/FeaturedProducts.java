package com.example.auctionapp.util;

import com.example.auctionapp.entity.BidEntity;
import com.example.auctionapp.entity.BoughtProductEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.repository.BidRepository;
import com.example.auctionapp.repository.BoughtProductRepository;
import com.example.auctionapp.repository.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeaturedProducts {
    public static UUID getFeaturedProducts(final UUID userId,
                                           final ProductRepository productRepository,
                                           final BidRepository bidRepository,
                                           final BoughtProductRepository boughtProductRepository) {
        final List<ProductEntity> seller = productRepository.findProductEntitiesByUserEntity_UserId(userId);
        final List<BidEntity> bids = bidRepository.findBidEntitiesByUserEntity_UserId(userId);
        final List<BoughtProductEntity> bought = boughtProductRepository.findBoughtProductEntitiesByUserEntity_UserId(userId);

        List<UUID> categoryIdsFromBids = fetchCategoryIds(bids.stream()
                .map(BidEntity::getProduct)
                .distinct()
                .toList());
        List<UUID> categoryIdsFromBought = fetchCategoryIds(bought.stream()
                .map(BoughtProductEntity::getProductEntity)
                .distinct()
                .toList());
        List<UUID> categoryIdsFromProducts = fetchCategoryIds(seller);


        List<UUID> joinedList = new ArrayList<>();
        Stream.of(categoryIdsFromBids, categoryIdsFromBought, categoryIdsFromProducts).forEach(joinedList::addAll);

        return fetchTopCategoryId(joinedList);
    }

    private static List<UUID> fetchCategoryIds(List<ProductEntity> products) {
        return products.stream()
                .map(productEntity -> productEntity.getCategory().getCategoryId())
                .distinct()
                .collect(Collectors.toList());
    }

    private static UUID fetchTopCategoryId(final List<UUID> categoryIds) {
        HashMap<UUID, Integer> frequencyMap = new HashMap<>();

        for (UUID categoryId : categoryIds) {
            frequencyMap.put(categoryId, frequencyMap.getOrDefault(categoryId, 0) + 1);
        }

        UUID mostFrequent = null;
        int maxCount = 0;

        for (Map.Entry<UUID, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        return mostFrequent;
    }
}
