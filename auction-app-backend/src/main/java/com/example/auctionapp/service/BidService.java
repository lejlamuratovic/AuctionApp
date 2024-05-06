package com.example.auctionapp.service;

import com.example.auctionapp.model.Bid;
import com.example.auctionapp.request.BidRequest;

import java.util.List;
import java.util.UUID;

public interface BidService {
    Bid placeBid(final BidRequest bidRequest);
    List<Bid> getBidsByUserId(final UUID userId);
}
