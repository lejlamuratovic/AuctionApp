package com.example.auctionapp.service;

import com.example.auctionapp.model.Bid;
import com.example.auctionapp.request.BidRequest;
import com.example.auctionapp.response.ProductBidDetailsResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BidService {
    Bid placeBid(final BidRequest bidRequest);
    Page<ProductBidDetailsResponse> getBidsForUser(final UUID userId, final int page, final int size);
    Page<Bid> getBidsByProductId(UUID productId, int page, int size);
}
