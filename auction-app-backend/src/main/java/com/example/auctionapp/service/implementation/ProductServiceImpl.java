package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.ProductImageEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.external.AmazonClient;
import com.example.auctionapp.repository.PaymentInfoRepository;
import com.example.auctionapp.repository.ProductImageRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.ProductAddRequest;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.response.BidSummaryResponse;
import com.example.auctionapp.response.ProductSearchResponse;
import com.example.auctionapp.service.ProductService;
import com.example.auctionapp.specification.ProductSpecification;
import com.example.auctionapp.util.ComputeSuggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PaymentInfoRepository paymentInfoRepository;
    private final AmazonClient amazonClient;
    private final ProductImageRepository productImageRepository;

    public ProductServiceImpl(final ProductRepository productRepository, final CategoryRepository categoryRepository, UserRepository userRepository, PaymentInfoRepository paymentInfoRepository, AmazonClient amazonClient, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.paymentInfoRepository = paymentInfoRepository;
        this.amazonClient = amazonClient;
        this.productImageRepository = productImageRepository;
    }

    @Override
    public ProductSearchResponse getProducts(UUID categoryId, String searchProduct, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<ProductEntity> specification = ProductSpecification.withDynamicQuery(categoryId, searchProduct);

        final Page<Product> products = productRepository.findAll(specification, pageable).map(ProductEntity::toDomainModel);
        String suggestedQuery = null;

        if (products.getTotalElements() < size && searchProduct != null && !searchProduct.isBlank()) {
            final List<String> productNames = this.productRepository.findAllProductNames();
            suggestedQuery = ComputeSuggestion.suggestCorrection(productNames, searchProduct);

            if (suggestedQuery != null && !suggestedQuery.equalsIgnoreCase(searchProduct)) {
                suggestedQuery = suggestedQuery;
            } else {
                suggestedQuery = null;
            }
        }

        return new ProductSearchResponse(products, suggestedQuery);
    }

    @Override
    public Product getProductById(final UUID id) {
        final ProductEntity productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));

        return productEntity.toDomainModel();
    }

    @Transactional
    @Override
    public Product addProduct(final ProductAddRequest productRequest, final List<MultipartFile> images) {
        ProductEntity productEntity = productRequest.toEntity();
        PaymentInfoEntity paymentInfoEntity;

        if (productRequest.isDataChanged() && productRequest.getUserId() != null) {
            UserEntity user = userRepository.findById(productRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

            paymentInfoEntity = user.getPaymentInfo();
        } else {
            paymentInfoEntity = new PaymentInfoEntity();

            paymentInfoEntity.setAddress(productRequest.getAddress());
            paymentInfoEntity.setCity(productRequest.getCity());
            paymentInfoEntity.setZipCode(productRequest.getZipCode());
            paymentInfoEntity.setExpirationDate(productRequest.getExpirationDate());
            paymentInfoEntity.setCardNumber(productRequest.getCardNumber());
            paymentInfoEntity.setNameOnCard(productRequest.getNameOnCard());
            paymentInfoEntity.setCountry(productRequest.getCountry());
            paymentInfoRepository.save(paymentInfoEntity);
        }

        productEntity.setPaymentInfo(paymentInfoEntity);
        productEntity.setStatus(ProductStatus.ACTIVE);

        if (productRequest.getCategoryId() != null) {
            productEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist")));
        }

        if (productRequest.getUserId() != null) {
            productEntity.setUserEntity(userRepository.findById(productRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist")));
        }

        productEntity = productRepository.saveAndFlush(productEntity);

        ProductEntity savedProduct = productEntity;

        List<ProductImageEntity> imageEntities = images.stream().map(image -> {
            try {
                final String imageUrl = amazonClient.uploadFile(image);

                ProductImageEntity imageEntity = new ProductImageEntity();
                imageEntity.setImageUrl(imageUrl);
                imageEntity.setProductEntity(savedProduct);

                productImageRepository.save(imageEntity);

                return imageEntity;
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }).collect(toList());

        productEntity.setProductImages(imageEntities);

        return productRepository.save(productEntity).toDomainModel();
    }

    @Override
    public Product updateProduct(final UUID id, final ProductAddRequest productRequest) {
        final ProductEntity existingProductEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with the given ID does not exist"));

        existingProductEntity.setName(productRequest.getName());
        existingProductEntity.setDescription(productRequest.getDescription());
        existingProductEntity.setStartPrice(productRequest.getStartPrice());
        existingProductEntity.setStartDate(productRequest.getStartDate());
        existingProductEntity.setEndDate(productRequest.getEndDate());
        existingProductEntity.setStatus(ProductStatus.ACTIVE);

        if (productRequest.getCategoryId() != null) {
            existingProductEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found")));
        }

        if(productRequest.getUserId() != null) {
            existingProductEntity.setUserEntity(userRepository.findById(productRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist")));
        }

        return this.productRepository.save(existingProductEntity).toDomainModel();
    }

    @Override
    public void deleteProduct(final UUID id) {
        final ProductEntity productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        this.productRepository.delete(productEntity);
    }

    public Page<Product> getProductsByCriteria(final int page, final int size, final String type) {
        Sort.Direction direction = Sort.Direction.DESC;
        String sortBy = "startDate";

        if ("lastChance".equals(type)) {
            direction = Sort.Direction.ASC;
            sortBy = "endDate";
        }

        Sort sort = Sort.by(direction, sortBy).and(Sort.by(Sort.Direction.ASC, "productId"));
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAll(pageable).map(ProductEntity::toDomainModel);
    }

    @Override
    public Product getRandomProduct() {
        ProductEntity randomProductEntity = productRepository.findRandomProduct()
                .orElseThrow(() -> new ResourceNotFoundException("No products available"));

        return randomProductEntity.toDomainModel();
    }

    @Override
    public BidSummaryResponse getBidSummary(final UUID productId) {
        final ProductEntity productEntity = this.productRepository.findProductEntityByProductId(productId)
                .orElseThrow(() -> new IllegalArgumentException("No bids found for the product."));

        return new BidSummaryResponse(productEntity.getHighestBid(), productEntity.getBidsCount());
    }
}
