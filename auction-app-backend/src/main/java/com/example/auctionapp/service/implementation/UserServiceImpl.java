package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.CreditCardEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.CreditCard;
import com.example.auctionapp.model.PaymentInfo;
import com.example.auctionapp.model.User;
import com.example.auctionapp.repository.CreditCardRepository;
import com.example.auctionapp.repository.PaymentInfoRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.CreditCardAddRequest;
import com.example.auctionapp.request.PaymentAddRequest;
import com.example.auctionapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final CreditCardRepository creditCardRepository;

    public UserServiceImpl(final UserRepository userRepository,
                           final PaymentInfoRepository paymentInfoRepository,
                           final CreditCardRepository creditCardRepository) {
        this.userRepository = userRepository;
        this.paymentInfoRepository = paymentInfoRepository;
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    public User getUser(final UUID userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        return userEntity.toDomainModel();
    }

    @Override
    public PaymentInfo addOrUpdatePaymentInfo(final UUID userId, final PaymentAddRequest paymentRequest) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        PaymentInfoEntity paymentInfoEntity = userEntity.getPaymentInfoEntity();

        if (paymentInfoEntity == null) {
            paymentInfoEntity = new PaymentInfoEntity();

            mapPaymentInfoDetails(paymentInfoEntity, paymentRequest);
            paymentInfoRepository.save(paymentInfoEntity);
            userEntity.setPaymentInfoEntity(paymentInfoEntity);

            userRepository.save(userEntity);
        } else {
            mapPaymentInfoDetails(paymentInfoEntity, paymentRequest);

            paymentInfoRepository.save(paymentInfoEntity);
        }

        return paymentInfoEntity.toDomainModel();
    }

    @Override
    public CreditCard updateCreditCardDetails(final UUID userId, final CreditCardAddRequest addNewCreditCard) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        PaymentInfoEntity paymentInfoEntity = userEntity.getPaymentInfoEntity();
        if (paymentInfoEntity == null) {
            throw new ResourceNotFoundException("User does not have existing payment information");
        }

        CreditCardEntity creditCardEntity = paymentInfoEntity.getCreditCardEntity();

        if (creditCardEntity == null) {
            // create new credit card details if none exists
            creditCardEntity = new CreditCardEntity();

            paymentInfoEntity.setCreditCardEntity(creditCardEntity);
        }

        creditCardEntity.setNameOnCard(addNewCreditCard.getNameOnCard());
        creditCardEntity.setCardNumber(addNewCreditCard.getCardNumber());
        creditCardEntity.setExpirationDate(addNewCreditCard.getExpirationDate());

        creditCardRepository.save(creditCardEntity); // save the credit card
        paymentInfoRepository.save(paymentInfoEntity); // update relation

        return creditCardEntity.toDomainModel();
    }

    private void mapPaymentInfoDetails(PaymentInfoEntity paymentInfoEntity, PaymentAddRequest paymentRequest) {
        paymentInfoEntity.setAddress(paymentRequest.getAddress());
        paymentInfoEntity.setCity(paymentRequest.getCity());
        paymentInfoEntity.setCountry(paymentRequest.getCountry());
        paymentInfoEntity.setZipCode(paymentRequest.getZipCode());
    }

}
