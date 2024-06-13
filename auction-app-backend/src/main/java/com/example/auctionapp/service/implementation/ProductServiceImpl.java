package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.ProductImageEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.exceptions.amazon.ImageUploadException;
import com.example.auctionapp.external.AmazonClient;
import com.example.auctionapp.repository.BidRepository;
import com.example.auctionapp.repository.BoughtProductRepository;
import com.example.auctionapp.repository.ProductImageRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.GetProductRequest;
import com.example.auctionapp.request.PaymentAddRequest;
import com.example.auctionapp.request.ProductAddRequest;
import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.repository.CategoryRepository;
import com.example.auctionapp.repository.ProductRepository;
import com.example.auctionapp.response.BidSummaryResponse;
import com.example.auctionapp.response.ProductBidDetailsResponse;
import com.example.auctionapp.response.ProductPrices;
import com.example.auctionapp.response.ProductSearchResponse;
import com.example.auctionapp.service.PaymentService;
import com.example.auctionapp.service.ProductService;
import com.example.auctionapp.specification.ProductSpecification;
import com.example.auctionapp.util.ComputeSuggestion;
import com.example.auctionapp.util.PageableUtil;
import com.example.auctionapp.util.FeaturedProducts;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
import com.example.auctionapp.util.csv.CsvUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;
    private final AmazonClient amazonClient;
    private final ProductImageRepository productImageRepository;
    private final BoughtProductRepository boughtProductRepository;
    private final BidRepository bidRepository;

    public ProductServiceImpl(final ProductRepository productRepository,
                              final CategoryRepository categoryRepository,
                              final UserRepository userRepository,
                              final AmazonClient amazonClient,
                              final ProductImageRepository productImageRepository,
                              final PaymentService paymentService,
                              final BoughtProductRepository boughtProductRepository,
                              final BidRepository bidRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
        this.amazonClient = amazonClient;
        this.productImageRepository = productImageRepository;
        this.boughtProductRepository = boughtProductRepository;
        this.bidRepository = bidRepository;
    }

    @Override
    public ProductSearchResponse getProducts(final GetProductRequest getProductRequest) {
        final Pageable pageable = PageableUtil.createPageable(
                getProductRequest.getPage(),
                getProductRequest.getSize(),
                getProductRequest.getSortField(),
                getProductRequest.getSortDirection()
        );

        final Page<Product> products = productRepository.findAll(ProductSpecification.buildSpecification(getProductRequest), pageable)
                                            .map(ProductEntity::toDomainModel);

        String suggestedQuery = null;
        if (products.getTotalElements() < getProductRequest.getSize() &&
                getProductRequest.getSearchProduct() != null &&
                !getProductRequest.getSearchProduct().isBlank()) {

            final List<String> productNames = productRepository.findAllProductNames();

            suggestedQuery = ComputeSuggestion.suggestCorrection(productNames, getProductRequest.getSearchProduct());

            if (suggestedQuery == null || suggestedQuery.equalsIgnoreCase(getProductRequest.getSearchProduct())) {
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
    public Product addProduct(ProductAddRequest productRequest, List<MultipartFile> images) {
        ProductEntity productEntity = productRequest.toEntity();

        final PaymentAddRequest paymentAddRequest = new PaymentAddRequest(
                productRequest.getAddress(),
                productRequest.getCity(),
                productRequest.getCountry(),
                productRequest.getZipCode(),
                productRequest.getNameOnCard(),
                productRequest.getCardNumber(),
                productRequest.getExpirationDate()
        );

        productEntity.setPaymentInfo(paymentService.addNewPaymentInfo(paymentAddRequest).toEntity());
        productEntity.setStatus(ProductStatus.ACTIVE);

        handleCategoryAndUser(productEntity, productRequest);

        productEntity = productRepository.saveAndFlush(productEntity);

        handleProductImages(productEntity, images);

        return productRepository.save(productEntity).toDomainModel();
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

        return productRepository
                .findProductEntitiesByStatusEquals(pageable, ProductStatus.ACTIVE)
                .map(ProductEntity::toDomainModel);
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

    @Override
    public Page<ProductBidDetailsResponse> getProductByUserAndStatus(final UUID userId,
                                                                     final ProductStatus productStatus,
                                                                     final int page,
                                                                     final int size) {
        Pageable pageable = PageRequest.of(page, size);

        return this.productRepository
                .findProductEntityByUserEntity_UserIdAndAndStatus(userId, productStatus, pageable)
                .map(ProductBidDetailsResponse::new);
    }

    @Override
    public List<Product> getFeaturedProductsByUser(final UUID userId, final int count) {
        final UUID categoryId = FeaturedProducts.getFeaturedProducts(userId,
                                                                    productRepository,
                                                                    bidRepository,
                                                                    boughtProductRepository);

        if (categoryId == null) {
            return getFeaturedProducts(count);
        }

        List<ProductEntity> topProducts = productRepository
                .findTopPopularProductEntitiesByCategoryId(categoryId, PageRequest.of(0, 10));

        if (topProducts.isEmpty()) {
            return getFeaturedProducts(count);
        }

        Collections.shuffle(topProducts);
        return topProducts.stream()
                .map(ProductEntity::toDomainModel)
                .limit(count)
                .toList();
    }

    @Override
    public List<Product> getFeaturedProducts(final int count) {
        List<ProductEntity> topProducts = productRepository
                .findMostPopularProducts(PageRequest.of(0, 10));

        Collections.shuffle(topProducts);

        return topProducts.stream()
                .map(ProductEntity::toDomainModel)
                .limit(count)
                .toList();
    }

    public boolean hasActiveProducts(final UUID userId) {
        final List<ProductEntity> activeProducts = this.productRepository
                .findProductEntityByUserEntity_UserIdAndAndStatusAndBidsCountIsGreaterThan(userId, ProductStatus.ACTIVE, 0);

        return activeProducts.isEmpty();
    }

    @Transactional
    @Override
    public void deleteActiveProducts(final UUID userId) {
        this.productRepository.deleteAllByUserEntity_UserIdAndStatus(userId, ProductStatus.ACTIVE);
    }
    
    @Override
    public ProductPrices getProductPrices() {
        final List<BigDecimal[]> pricesList = productRepository.findMinAndMaxPrices();

        if (!pricesList.isEmpty()) {
            final BigDecimal[] prices = pricesList.get(0);

            return GenericBuilder.of(ProductPrices::new)
                    .with(ProductPrices::setMinPrice, prices[0])
                    .with(ProductPrices::setMaxPrice, prices[1])
                    .build();
        } else {
            return GenericBuilder.of(ProductPrices::new)
                    .with(ProductPrices::setMinPrice, BigDecimal.ZERO)
                    .with(ProductPrices::setMaxPrice, BigDecimal.ZERO)
                    .build();
        }
    }

    @Override
    public List<Product> getRandomProductsByCategoryId(final UUID categoryId, final int count) {
        this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist"));

        final List<ProductEntity> products =
                this.productRepository.findTopPopularProductEntitiesByCategoryId(categoryId, PageRequest.of(0, 10));

        Collections.shuffle(products);

        return products
                .stream()
                .map(ProductEntity::toDomainModel)
                .limit(count)
                .toList();
    }

    public List<Product> uploadProducts(final MultipartFile file, final UUID userId) throws IOException {
        final UserEntity user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist"));

        return CsvUtil.uploadProduct(file, user, categoryRepository)
                .stream()
                .map(ProductEntity::toDomainModel)
                .toList();
    }

    private void handleCategoryAndUser(ProductEntity productEntity, ProductAddRequest productRequest) {
        if (productRequest.getCategoryId() != null) {
            productEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with the given ID does not exist")));
        }

        if (productRequest.getUserId() != null) {
            productEntity.setUserEntity(userRepository.findById(productRequest.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with the given ID does not exist")));
        }
    }

    private void handleProductImages(ProductEntity productEntity, List<MultipartFile> images) {
        final List<ProductImageEntity> imageEntities = images.stream().map(image -> {
            try {
                String imageUrl = amazonClient.uploadFile(image);
                ProductImageEntity imageEntity = new ProductImageEntity();

                imageEntity.setImageUrl(imageUrl);
                imageEntity.setProductEntity(productEntity);

                productImageRepository.save(imageEntity);

                return imageEntity;
            } catch (Exception e) {
                throw new ImageUploadException("Failed to upload image");
            }
        }).collect(toList());

        productEntity.setProductImages(imageEntities);
    }
}
