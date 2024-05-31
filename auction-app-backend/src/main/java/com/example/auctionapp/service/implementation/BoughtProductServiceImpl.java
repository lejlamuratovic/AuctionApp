package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.BoughtProductEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.BoughtProduct;
import com.example.auctionapp.repository.BoughtProductRepository;
import com.example.auctionapp.repository.PaymentInfoRepository;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.service.BoughtProductService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BoughtProductServiceImpl implements BoughtProductService {
    private final BoughtProductRepository boughtProductRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PaymentInfoRepository paymentInfoRepository;

    public BoughtProductServiceImpl(final BoughtProductRepository boughtProductRepository,
                                    final UserRepository userRepository,
                                    final ProductRepository productRepository,
                                    final PaymentInfoRepository paymentInfoRepository) {
        this.boughtProductRepository = boughtProductRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.paymentInfoRepository = paymentInfoRepository;
    }

    @Override
    public BoughtProduct saveBoughtProduct(UUID buyer_id, UUID product_id, UUID payment_info_id) {
        BoughtProductEntity boughtProductEntity = new BoughtProductEntity();

        ProductEntity productEntity = this.productRepository.findById(product_id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID not found"));

        UserEntity userEntity = this.userRepository.findById(buyer_id)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID not found"));

        PaymentInfoEntity paymentInfoEntity = this.paymentInfoRepository.findById(payment_info_id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with the given ID not found"));

        boughtProductEntity.setUserEntity(userEntity);
        boughtProductEntity.setProductEntity(productEntity);
        boughtProductEntity.setPaymentInfoEntity(paymentInfoEntity);

        return this.boughtProductRepository.save(boughtProductEntity).toDomainModel();
    }
}
