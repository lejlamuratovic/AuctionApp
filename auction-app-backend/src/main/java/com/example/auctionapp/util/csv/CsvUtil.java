package com.example.auctionapp.util.csv;

import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.entity.CreditCardEntity;
import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.entity.ProductImageEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class CsvUtil {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static List<ProductEntity> uploadProduct(final MultipartFile file,
                                             final UserEntity user,
                                             final CategoryRepository categoryRepository
    ) throws IOException {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            HeaderColumnNameMappingStrategy<ProductCsvRepresentation> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(ProductCsvRepresentation.class);

            CsvToBean<ProductCsvRepresentation> csvToBean = new CsvToBeanBuilder<ProductCsvRepresentation>(reader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse().stream()
                    .map(csvLine -> {
                        final ProductEntity productEntity = buildProductEntity(csvLine, user, categoryRepository);
                        handleProductImagesFromUrls(productEntity, csvLine.getImages());
                        return productEntity;
                    })
                    .toList();
        }
    }

    private static ProductEntity buildProductEntity(final ProductCsvRepresentation csvLine,
                                             final UserEntity user,
                                             final CategoryRepository categoryRepository) {
        LocalDate startDate = LocalDate.parse(csvLine.getStartDate(), FORMATTER);
        LocalDate endDate = LocalDate.parse(csvLine.getEndDate(), FORMATTER);

        final CategoryEntity category = categoryRepository.findByName(csvLine.getCategoryName())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        final CreditCardEntity creditCard = GenericBuilder.of(CreditCardEntity::new)
                .with(CreditCardEntity::setNameOnCard, csvLine.getNameOnCard())
                .with(CreditCardEntity::setCardNumber, csvLine.getCardNumber())
                .with(CreditCardEntity::setExpirationDate, LocalDate.parse(csvLine.getExpirationDate(), FORMATTER))
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
                .with(ProductEntity::setStartPrice, new BigDecimal(csvLine.getStartPrice()))
                .with(ProductEntity::setStartDate, startDate.atStartOfDay())
                .with(ProductEntity::setEndDate, endDate.atStartOfDay())
                .with(ProductEntity::setCategory, category)
                .with(ProductEntity::setUserEntity, user)
                .with(ProductEntity::setStatus, ProductStatus.ACTIVE)
                .with(ProductEntity::setPaymentInfo, paymentInfo)
                .build();
    }

    private static void handleProductImagesFromUrls(final ProductEntity productEntity, final String imageUrls) {
        List<ProductImageEntity> imageEntities = Arrays.stream(imageUrls.split(";"))
                .map(url -> {
                    ProductImageEntity imageEntity = new ProductImageEntity();
                    imageEntity.setImageUrl(url);
                    imageEntity.setProductEntity(productEntity);
                    
                    return imageEntity;
                })
                .toList();

        productEntity.setProductImages(imageEntities);
    }
}
