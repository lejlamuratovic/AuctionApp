package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.CreditCardEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.User;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.UserDetailsRequest;
import com.example.auctionapp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(final UUID userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        return userEntity.toDomainModel();
    }

    @Transactional
    @Override
    public User updateUser(UUID userId, UserDetailsRequest userRequest) {
        final UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setDob(userRequest.getDob());

        if (userEntity.getPaymentInfoEntity() == null) {
            userEntity.setPaymentInfoEntity(new PaymentInfoEntity());
        }

        final PaymentInfoEntity paymentInfoEntity = userEntity.getPaymentInfoEntity();

        paymentInfoEntity.setAddress(userRequest.getAddress());
        paymentInfoEntity.setCity(userRequest.getCity());
        paymentInfoEntity.setZipCode(userRequest.getZipCode());
        paymentInfoEntity.setCountry(userRequest.getCountry());

        if (paymentInfoEntity.getCreditCardEntity() == null) {
            paymentInfoEntity.setCreditCardEntity(new CreditCardEntity());
        }

        final CreditCardEntity creditCardEntity = paymentInfoEntity.getCreditCardEntity();

        creditCardEntity.setNameOnCard(userRequest.getNameOnCard());
        creditCardEntity.setExpirationDate(userRequest.getExpirationDate());
        creditCardEntity.setCardNumber(userRequest.getCardNumber());

        paymentInfoEntity.setCreditCardEntity(creditCardEntity);
        userEntity.setPaymentInfo(paymentInfoEntity);

        return this.userRepository.save(userEntity).toDomainModel();
    }
}
