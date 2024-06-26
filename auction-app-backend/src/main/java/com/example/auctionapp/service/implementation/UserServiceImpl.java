package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.CreditCardEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.amazon.ImageUploadException;
import com.example.auctionapp.exceptions.authentication.CannotDeactivateAccount;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.external.AmazonClient;
import com.example.auctionapp.model.User;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.UserDetailsRequest;
import com.example.auctionapp.service.ProductService;
import com.example.auctionapp.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AmazonClient amazonClient;
    private final ProductService productService;

    public UserServiceImpl(final UserRepository userRepository,
                           final AmazonClient amazonClient,
                           final ProductService productService) {
        this.userRepository = userRepository;
        this.amazonClient = amazonClient;
        this.productService = productService;
    }

    @Override
    public User getUser(final UUID userId) {
        final UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        return userEntity.toDomainModel();
    }

    @Transactional
    @Override
    public User updateUser(final UUID userId, final UserDetailsRequest userRequest) {
        final UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setDateOfBirth(userRequest.getDateOfBirth());

        if (userEntity.getPaymentInfoEntity() == null) {
            userEntity.setPaymentInfoEntity(new PaymentInfoEntity());
        }

        final PaymentInfoEntity paymentInfoEntity = userEntity.getPaymentInfoEntity();

        paymentInfoEntity.setAddress(userRequest.getAddress());
        paymentInfoEntity.setCity(userRequest.getCity());
        paymentInfoEntity.setZipCode(userRequest.getZipCode());
        paymentInfoEntity.setCountry(userRequest.getCountry());
        paymentInfoEntity.setState(userRequest.getState());

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

    @Override
    public User updateProfileImage(final UUID userId, final MultipartFile image) {
        final UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        try {
            final String imageUrl = amazonClient.uploadFile(image);

            userEntity.setProfilePicture(imageUrl);

            userRepository.save(userEntity);
        } catch (Exception e) {
            throw new ImageUploadException("Failed to upload image");
        }

        return userEntity.toDomainModel();
    }

    @Transactional
    @Override
    public void deactivateAccount(final UUID userId) {
        final UserEntity userEntity = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        if(!this.productService.hasActiveProducts(userId)) {
            throw new CannotDeactivateAccount("User has active products with bids");
        }

        userEntity.setActive(false);

        this.productService.deleteActiveProducts(userId);

        this.userRepository.save(userEntity);
    }
}
