package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.BoughtProductEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.BoughtProduct;
import com.example.auctionapp.repository.BoughtProductRepository;
import com.example.auctionapp.repository.PaymentInfoRepository;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.service.BoughtProductService;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
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
    public BoughtProduct saveBoughtProduct(final UUID productId, final UUID buyerId, final UUID paymentInfoId) {
        ProductEntity productEntity = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID not found"));

        UserEntity userEntity = this.userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID not found"));

        PaymentInfoEntity paymentInfoEntity = this.paymentInfoRepository.findById(paymentInfoId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with the given ID not found"));

        productEntity.setStatus(ProductStatus.SOLD);
        productEntity.setPaymentInfo(paymentInfoEntity);

        final BoughtProductEntity boughtProductEntity = GenericBuilder.of(BoughtProductEntity::new)
                .with(BoughtProductEntity::setUserEntity, userEntity)
                .with(BoughtProductEntity::setProductEntity, productEntity)
                .with(BoughtProductEntity::setPaymentInfoEntity, paymentInfoEntity)
                .build();

        return this.boughtProductRepository.save(boughtProductEntity).toDomainModel();
    }
}
