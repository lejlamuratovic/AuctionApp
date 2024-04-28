package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.BidEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.Bid;
import com.example.auctionapp.repository.BidRepository;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.BidRequest;
import com.example.auctionapp.service.BidService;
import com.example.auctionapp.service.NotificationService;
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

        if (bidRequest.getUserId() != null) {
            bidEntity.setUser(userRepository.findById(bidRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist")));
        }

        if (bidRequest.getProductId() != null) {
            bidEntity.setProduct(productRepository.findById(bidRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist")));
        }

        // check if its highest bid
        final BigDecimal highestBid = bidRepository.findHighestBidByProductId(bidRequest.getProductId());

        if (highestBid == null || bidRequest.getBidAmount().compareTo(highestBid) > 0) {
            bidRepository.save(bidEntity); // bid higher, save it

            notificationService.notifyUser(bidRequest.getUserId().toString(),
                    HIGHEST_BID);
        } else {
            notificationService.notifyUser(bidRequest.getUserId().toString(),
                    LOWER_BID);
        }

        return bidEntity.toDomainModel();
    }
}
