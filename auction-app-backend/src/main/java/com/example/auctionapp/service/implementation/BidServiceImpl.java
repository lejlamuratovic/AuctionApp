package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.BidEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.Bid;
import com.example.auctionapp.repository.BidRepository;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.BidRequest;
import com.example.auctionapp.service.BidService;
import com.example.auctionapp.service.NotificationService;
import com.example.auctionapp.util.ValidationUtility;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BidServiceImpl implements BidService {
    private final BidRepository bidRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final NotificationService notificationService;
    public static final String HIGHEST_BID = "Congratulations! You outbid the competition";
    public static final String LOWER_BID = "There are higher bids than yours. Give it a second try";

    public BidServiceImpl(BidRepository bidRepository, UserRepository userRepository, ProductRepository productRepository, NotificationService notificationService) {
        this.bidRepository = bidRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.notificationService = notificationService;
    }

    public Bid placeBid(final BidRequest bidRequest) {
        BidEntity bidEntity = bidRequest.toEntity();

        if (bidRequest.getProductId() != null) {
            final ProductEntity product = productRepository.findById(bidRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));

            ValidationUtility.validateBidTime(bidRequest.getBidTime(), product);
            ValidationUtility.validateBidAmount(bidRequest.getBidAmount(), product);
            ValidationUtility.productOwner(bidRequest.getUserId(), product.getUserEntity().getUserId());

            bidEntity.setProduct(product);
        }

        if (bidRequest.getUserId() != null) {
            bidEntity.setUser(userRepository.findById(bidRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist")));
        }

        // retrieve the current highest bid entity
        final BidEntity highestBidEntity = bidRepository.findHighestBidByProductId(bidRequest.getProductId());
        final BigDecimal highestBidAmount = highestBidEntity != null ? highestBidEntity.getBidAmount() : null;

        if (highestBidAmount == null || bidRequest.getBidAmount().compareTo(highestBidAmount) > 0) {
            // update the previous highest bidder
            if (highestBidEntity != null) {
                notificationService.notifyUser(highestBidEntity.getUser().getUserId(), LOWER_BID);
            }

            bidRepository.save(bidEntity); // save the highest bid
            notificationService.notifyUser(bidRequest.getUserId(), HIGHEST_BID);
        } else {
            notificationService.notifyUser(bidRequest.getUserId(), LOWER_BID);
        }

        return bidEntity.toDomainModel();
    }
}
