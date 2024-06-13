package com.example.auctionapp.util;

import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.entity.CreditCardEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.repository.CategoryRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.ProductAddRequest;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CsvUtil {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ProductEntity> uploadProduct(final MultipartFile file) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<ProductAddRequest> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProductAddRequest.class);

            CsvToBean<ProductAddRequest> csvToBean = new CsvToBeanBuilder<ProductAddRequest>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreEmptyLine(true)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse().stream()
                    .map(csvLine -> {
                        final CategoryEntity category = categoryRepository.findByName(csvLine.getCategoryName())
                                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

                        final UserEntity user = userRepository.findByEmail(csvLine.getEmail())
                                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

                        final CreditCardEntity creditCard = GenericBuilder.of(CreditCardEntity::new)
                                .with(CreditCardEntity::setNameOnCard, csvLine.getNameOnCard())
                                .with(CreditCardEntity::setCardNumber, csvLine.getCardNumber())
                                .with(CreditCardEntity::setExpirationDate, csvLine.getExpirationDate())
                                .build();

                        final PaymentInfoEntity paymentInfo = GenericBuilder.of(PaymentInfoEntity::new)
                                .with(PaymentInfoEntity::setCreditCardEntity, creditCard)
                                .with(PaymentInfoEntity::setCountry, csvLine.getCountry())
                                .with(PaymentInfoEntity::setCity, csvLine.getCity())
                                .with(PaymentInfoEntity::setAddress, csvLine.getAddress())
                                .with(PaymentInfoEntity::setZipCode, csvLine.getZipCode())
                                .build();

                        return GenericBuilder.of(ProductEntity::new)
                                .with(ProductEntity::setName, csvLine.getName())
                                .with(ProductEntity::setDescription, csvLine.getDescription())
                                .with(ProductEntity::setStartPrice, csvLine.getStartPrice())
                                .with(ProductEntity::setStartDate, csvLine.getStartDate())
                                .with(ProductEntity::setEndDate, csvLine.getEndDate())
                                .with(ProductEntity::setCategory, category)
                                .with(ProductEntity::setUserEntity, user)
                                .with(ProductEntity::setStatus, ProductStatus.ACTIVE)
                                .with(ProductEntity::setPaymentInfo, paymentInfo)
                                .build();
                    })
                    .collect(Collectors.toList());
        }
    }
}
