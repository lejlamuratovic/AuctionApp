package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.CreditCardEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.model.PaymentInfo;
import com.example.auctionapp.repository.CreditCardRepository;
import com.example.auctionapp.repository.PaymentInfoRepository;
import com.example.auctionapp.request.PaymentAddRequest;
import com.example.auctionapp.request.StripePaymentAddRequest;
import com.example.auctionapp.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentInfoRepository paymentInfoRepository;
    private final CreditCardRepository creditCardRepository;

    public PaymentServiceImpl(final PaymentInfoRepository paymentInfoRepository,
                              final CreditCardRepository creditCardRepository) {
        this.paymentInfoRepository = paymentInfoRepository;
        this.creditCardRepository = creditCardRepository;
    }

    // used for payments with stripe
    @Transactional
    @Override
    public PaymentInfo addNewPaymentInfo(StripePaymentAddRequest stripePaymentAddRequest) {
        CreditCardEntity creditCardEntity = new CreditCardEntity();

        creditCardEntity.setStripeToken(stripePaymentAddRequest.getStripeToken());
        creditCardRepository.save(creditCardEntity);

        PaymentInfoEntity paymentInfo = new PaymentInfoEntity();
        paymentInfo.setCreditCardEntity(creditCardEntity);
        paymentInfoRepository.save(paymentInfo);

        return paymentInfo.toDomainModel();
    }

    // used for adding payment information to a product
    @Transactional
    @Override
    public PaymentInfo addNewPaymentInfo(PaymentAddRequest paymentAddRequest) {
        CreditCardEntity creditCardEntity = new CreditCardEntity();

        creditCardEntity.setCardNumber(paymentAddRequest.getCardNumber());
        creditCardEntity.setExpirationDate(paymentAddRequest.getExpirationDate());
        creditCardEntity.setNameOnCard(paymentAddRequest.getNameOnCard());

        creditCardRepository.save(creditCardEntity);

        PaymentInfoEntity paymentInfo = paymentAddRequest.toEntity();

        paymentInfo.setCreditCardEntity(creditCardEntity);
        paymentInfoRepository.save(paymentInfo);

        return paymentInfo.toDomainModel();
    }
}
