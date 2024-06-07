package com.example.auctionapp.util;

import com.example.auctionapp.entity.BidEntity;
import com.example.auctionapp.entity.BoughtProductEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.repository.BidRepository;
import com.example.auctionapp.repository.BoughtProductRepository;
import com.example.auctionapp.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class FeaturedProducts {
    private static final Logger logger = LoggerFactory.getLogger(FeaturedProducts.class);

    private static final int WEIGHT_BUY = 3;
    private static final int WEIGHT_BID = 2;
    private static final int WEIGHT_SELL = 1;

    public static UUID getFeaturedProducts(final UUID userId,
                                           final ProductRepository productRepository,
                                           final BidRepository bidRepository,
                                           final BoughtProductRepository boughtProductRepository) {
        logger.info("Fetching products for user ID: {}", userId);

        final List<ProductEntity> seller = productRepository.findProductEntitiesByUserEntity_UserId(userId);
        logger.info("Fetched {} products for sale by user ID: {}", seller.size(), userId);

        final List<BidEntity> bids = bidRepository.findBidEntitiesByUserEntity_UserId(userId);
        logger.info("Fetched {} bids by user ID: {}", bids.size(), userId);

        final List<BoughtProductEntity> bought = boughtProductRepository.findBoughtProductEntitiesByUserEntity_UserId(userId);
        logger.info("Fetched {} bought products by user ID: {}", bought.size(), userId);

        Map<UUID, Integer> frequencyMap = new HashMap<>();

        addToFrequencyMap(fetchCategoryIds(seller), frequencyMap, WEIGHT_SELL);
        addToFrequencyMap(fetchCategoryIds(bought.stream()
                                            .map(BoughtProductEntity::getProductEntity)
                                            .distinct()
                                            .toList()),
                                            frequencyMap,
                                            WEIGHT_BUY);
        addToFrequencyMap(fetchCategoryIds(bids.stream()
                                            .map(BidEntity::getProduct)
                                            .distinct()
                                            .toList()),
                                            frequencyMap,
                                            WEIGHT_BID);

        UUID featuredCategoryId = fetchTopCategoryId(frequencyMap);
        logger.info("Featured category ID for user ID {}: {}", userId, featuredCategoryId);

        return featuredCategoryId;
    }

    private static void addToFrequencyMap(final List<UUID> categoryIds,
                                          final Map<UUID, Integer> frequencyMap,
                                          final int weight) {
        for (UUID categoryId : categoryIds) {
            frequencyMap.put(categoryId, frequencyMap.getOrDefault(categoryId, 0) + weight);

            logger.info("Category ID: {} has frequency: {}", categoryId, frequencyMap.get(categoryId));
        }
    }

    private static UUID fetchTopCategoryId(final Map<UUID, Integer> frequencyMap) {
        UUID mostFrequent = null;
        int maxCount = 0;

        for (Map.Entry<UUID, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostFrequent = entry.getKey();
            }
        }

        logger.info("Most frequent category ID: {} with count: {}", mostFrequent, maxCount);
        return mostFrequent;
    }

    private static List<UUID> fetchCategoryIds(final List<ProductEntity> products) {
        return products.stream()
                .map(ProductEntity::getCategoryEntity)
                .map(category -> category.getParentCategory() != null ?
                        category.getParentCategory().getCategoryId() :
                        category.getCategoryId())
                .distinct()
                .collect(Collectors.toList());
    }
}
