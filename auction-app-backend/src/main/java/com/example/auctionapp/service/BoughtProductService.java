package com.example.auctionapp.service;

import com.example.auctionapp.model.BoughtProduct;

import java.util.UUID;

public interface BoughtProductService {
    BoughtProduct saveBoughtProduct(final UUID buyer_id, final UUID product_id, final UUID payment_info_id);
}
