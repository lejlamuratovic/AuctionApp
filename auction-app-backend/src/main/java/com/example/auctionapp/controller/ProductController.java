package com.example.auctionapp.controller;

import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.exceptions.security.InvalidTokenException;
import com.example.auctionapp.model.Product;
import com.example.auctionapp.request.GetProductRequest;
import com.example.auctionapp.request.ProductAddRequest;
import com.example.auctionapp.response.BidSummaryResponse;
import com.example.auctionapp.response.ProductBidDetailsResponse;
import com.example.auctionapp.response.ProductPrices;
import com.example.auctionapp.response.ProductSearchResponse;
import com.example.auctionapp.service.ProductService;
import com.example.auctionapp.service.implementation.JwtService;
import com.example.auctionapp.util.SecurityRoles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "JWT Security")
public class ProductController {
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 8;

    private final ProductService productService;
    private final JwtService jwtService;

    public ProductController(final ProductService productService, final JwtService jwtService) {
        this.productService = productService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ProductSearchResponse getProducts(@ModelAttribute final GetProductRequest getProductRequest) {
        return this.productService.getProducts(getProductRequest);
    }

    @PreAuthorize(SecurityRoles.ALL)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addProduct(
            @RequestPart("product") ProductAddRequest productRequest,
            @RequestPart("images") List<MultipartFile> images
    ) {
        return productService.addProduct(productRequest, images);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable final UUID id) {
        return this.productService.getProductById(id);
    }

    @PreAuthorize(SecurityRoles.ALL)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable final UUID id) {
        this.productService.deleteProduct(id);
    }

    @GetMapping("/criteria")
    public Page<Product> getProductsByCriteria(
            @RequestParam(value = "page", defaultValue = "" + DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = "" + DEFAULT_PAGE_SIZE) final int size,
            @RequestParam(value = "type", defaultValue = "newArrivals") final String type) {
        return productService.getProductsByCriteria(page, size, type);
    }

    @GetMapping("/random")
    public Product getRandomProduct() {
        return this.productService.getRandomProduct();
    }

    @GetMapping("/{productId}/bid-details")
    public BidSummaryResponse getBidSummary(@PathVariable final UUID productId) {
        return this.productService.getBidSummary(productId);
    }

    @PreAuthorize(SecurityRoles.ALL)
    @GetMapping("/user-products")
    public Page<ProductBidDetailsResponse> getProductsByUserAndStatus(
            @RequestParam(value = "userId") final UUID userId,
            @RequestParam(value = "status") final ProductStatus status,
            @RequestParam(value = "page", defaultValue = "" + DEFAULT_PAGE_NUMBER) final int page,
            @RequestParam(value = "size", defaultValue = "" + DEFAULT_PAGE_SIZE) final int size) {
        return this.productService.getProductByUserAndStatus(userId, status, page, size);
    }

    @GetMapping("/featured-products/{userId}")
    public List<Product> getFeaturedProductsByUser(@PathVariable final UUID userId, @RequestParam final int count) {
        return this.productService.getFeaturedProductsByUser(userId, count);
    }

    @GetMapping("/featured-products")
    public List<Product> getFeaturedProducts(@RequestParam final int count) {
        return this.productService.getFeaturedProducts(count);
    }

    @GetMapping("/prices")
    public ProductPrices getProductPrices() {
        return this.productService.getProductPrices();
    }

    @GetMapping("/random/{categoryId}")
    public List<Product> getRandomProductsByCategoryId(@PathVariable final UUID categoryId, @RequestParam final int count) {
        return this.productService.getRandomProductsByCategoryId(categoryId, count);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Product> uploadProduct(@RequestPart("file") final MultipartFile file, HttpServletRequest request) throws IOException {
        UUID userId = null;
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            final String token = authorizationHeader.substring(7);

            try {
                userId = UUID.fromString
                        (jwtService.extractClaim(token, claims -> claims.get("id", String.class)));
            } catch (Exception e) {
                throw new InvalidTokenException("Token is not valid");
            }
        }

        if (userId == null) {
            throw new InvalidTokenException("Token is not valid");
        }

        return this.productService.uploadProducts(file, userId);
    }
}
