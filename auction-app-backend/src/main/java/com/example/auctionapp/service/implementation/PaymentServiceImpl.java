package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.CreditCardEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.model.PaymentInfo;
import com.example.auctionapp.repository.PaymentInfoRepository;
import com.example.auctionapp.request.PaymentAddRequest;
import com.example.auctionapp.request.StripePaymentAddRequest;
import com.example.auctionapp.service.BoughtProductService;
import com.example.auctionapp.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentInfoRepository paymentInfoRepository;
    private final BoughtProductService boughtProductService;

    public PaymentServiceImpl(final PaymentInfoRepository paymentInfoRepository,
                              final BoughtProductService boughtProductService) {
        this.paymentInfoRepository = paymentInfoRepository;
        this.boughtProductService = boughtProductService;
    }

    // used for payments with stripe
    @Transactional
    @Override
    public PaymentInfo addStripePaymentInfo(StripePaymentAddRequest stripePaymentAddRequest) {
        CreditCardEntity creditCardEntity = new CreditCardEntity();

        creditCardEntity.setStripeToken(stripePaymentAddRequest.getStripeToken());

        PaymentInfoEntity paymentInfo = stripePaymentAddRequest.toEntity();
        paymentInfo.setCreditCardEntity(creditCardEntity);

        PaymentInfoEntity paymentInfoEntity = this.paymentInfoRepository.saveAndFlush(paymentInfo);

        this.boughtProductService.saveBoughtProduct(stripePaymentAddRequest.getProductId(),
                                                    stripePaymentAddRequest.getBuyerId(),
                                                    paymentInfoEntity.getPaymentInfoId());

        return paymentInfoEntity.toDomainModel();
    }

    // used for adding payment information to a product
    @Override
    public PaymentInfo addNewPaymentInfo(PaymentAddRequest paymentAddRequest) {
        CreditCardEntity creditCardEntity = new CreditCardEntity();

        creditCardEntity.setCardNumber(paymentAddRequest.getCardNumber());
        creditCardEntity.setExpirationDate(paymentAddRequest.getExpirationDate());
        creditCardEntity.setNameOnCard(paymentAddRequest.getNameOnCard());

        PaymentInfoEntity paymentInfo = paymentAddRequest.toEntity();
        paymentInfo.setCreditCardEntity(creditCardEntity);

        return this.paymentInfoRepository.save(paymentInfo).toDomainModel();
    }
}
