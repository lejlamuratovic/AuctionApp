package com.example.auctionapp.entity;

import com.example.auctionapp.model.BoughtProduct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "bought_products", schema="auction_app")
public class BoughtProductEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "bought_product_id")
    private UUID boughtProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_info_id")
    private PaymentInfoEntity paymentInfoEntity;

    public BoughtProduct toDomainModel() {
        BoughtProduct boughtProduct = new BoughtProduct();

        boughtProduct.setBoughtProductId(this.boughtProductId);
        boughtProduct.setProductId(this.productEntity.getProductId());
        boughtProduct.setBuyerId(this.userEntity.getUserId());
        boughtProduct.setPaymentInfoIid(this.paymentInfoEntity.getPaymentInfoId());

        return boughtProduct;
    }

    public UUID getBoughtProductId() {
        return boughtProductId;
    }

    public void setBoughtProductId(UUID boughtProductId) {
        this.boughtProductId = boughtProductId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public PaymentInfoEntity getPaymentInfoEntity() {
        return paymentInfoEntity;
    }

    public void setPaymentInfoEntity(PaymentInfoEntity paymentInfoEntity) {
        this.paymentInfoEntity = paymentInfoEntity;
    }
}
