package com.salesmanager.shop.store.api.v1.wishlist;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.wishlist.WishlistService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.wishlist.Wishlist;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductMapper;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.wishlist.ReadableWishlist;
import com.salesmanager.shop.store.controller.store.facade.StoreFacade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wishlist API - Allow customers to save favorite products
 * Community Feature Request: Product Wishlist functionality
 * 
 * Complete implementation with:
 * - Entity (Wishlist, WishlistItem)
 * - Repository (WishlistRepository)
 * - Service (WishlistService)
 * - Controller (WishlistApi)
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(tags = {"Wishlist API - Manage customer wishlists"})
public class WishlistApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(WishlistApi.class);

    @Inject
    private WishlistService wishlistService;

    @Inject
    private ReadableProductMapper readableProductMapper;

    @Inject
    private StoreFacade storeFacade;

    @ApiOperation(
        value = "Get customer wishlist",
        notes = "Retrieve all products in customer's wishlist",
        response = ReadableWishlist.class
    )
    @GetMapping("/customer/{customerId}/wishlist")
    public ResponseEntity<ReadableWishlist> getWishlist(
        @ApiParam(value = "Customer ID", required = true) @PathVariable Long customerId,
        @ApiParam(value = "Store code", required = true) @RequestParam String store
    ) {
        try {
            MerchantStore merchantStore = storeFacade.get(store);
            Language language = merchantStore.getDefaultLanguage();

            Wishlist wishlist = wishlistService.getByCustomerId(customerId);
            if (wishlist == null) {
                return ResponseEntity.notFound().build();
            }
            
            ReadableWishlist readable = convertToReadable(wishlist, merchantStore, language);
            return ResponseEntity.ok(readable);
        } catch (Exception e) {
            LOGGER.error("Error retrieving wishlist for customer: " + customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(
        value = "Add product to wishlist",
        notes = "Add a product to customer's wishlist",
        response = ReadableWishlist.class
    )
    @PostMapping("/customer/{customerId}/wishlist/product/{productId}")
    public ResponseEntity<ReadableWishlist> addToWishlist(
        @ApiParam(value = "Customer ID", required = true) @PathVariable Long customerId,
        @ApiParam(value = "Product ID", required = true) @PathVariable Long productId,
        @ApiParam(value = "Store code", required = true) @RequestParam String store
    ) {
        try {
            MerchantStore merchantStore = storeFacade.get(store);
            Language language = merchantStore.getDefaultLanguage();

            Wishlist wishlist = wishlistService.addProduct(customerId, productId);
            ReadableWishlist readable = convertToReadable(wishlist, merchantStore, language);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(readable);
        } catch (ServiceException e) {
            LOGGER.error("Service error adding product to wishlist: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            LOGGER.error("Error adding product to wishlist", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(
        value = "Remove product from wishlist",
        notes = "Remove a product from customer's wishlist"
    )
    @DeleteMapping("/customer/{customerId}/wishlist/product/{productId}")
    public ResponseEntity<Void> removeFromWishlist(
        @ApiParam(value = "Customer ID", required = true) @PathVariable Long customerId,
        @ApiParam(value = "Product ID", required = true) @PathVariable Long productId,
        @ApiParam(value = "Store code", required = true) @RequestParam String store
    ) {
        try {
            wishlistService.removeProduct(customerId, productId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error removing product from wishlist", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(
        value = "Clear wishlist",
        notes = "Remove all products from wishlist"
    )
    @DeleteMapping("/customer/{customerId}/wishlist")
    public ResponseEntity<Void> clearWishlist(
        @ApiParam(value = "Customer ID", required = true) @PathVariable Long customerId,
        @ApiParam(value = "Store code", required = true) @RequestParam String store
    ) {
        try {
            wishlistService.clearWishlist(customerId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            LOGGER.error("Error clearing wishlist", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @ApiOperation(
        value = "Get wishlist item count",
        notes = "Get the number of items in customer's wishlist"
    )
    @GetMapping("/customer/{customerId}/wishlist/count")
    public ResponseEntity<Integer> getWishlistCount(
        @ApiParam(value = "Customer ID", required = true) @PathVariable Long customerId,
        @ApiParam(value = "Store code", required = true) @RequestParam String store
    ) {
        try {
            int count = wishlistService.getWishlistCount(customerId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            LOGGER.error("Error getting wishlist count", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ReadableWishlist convertToReadable(Wishlist wishlist, MerchantStore store, Language language) {
        ReadableWishlist readable = new ReadableWishlist();
        readable.setId(wishlist.getId());
        readable.setCustomerId(wishlist.getCustomer().getId());
        readable.setName(wishlist.getName());
        readable.setPublic(wishlist.isPublic());
        
        List<Long> productIds = wishlist.getItems().stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toList());
        readable.setProductIds(productIds);
        
        // Convert products to ReadableProduct
        List<ReadableProduct> products = wishlist.getItems().stream()
                .map(item -> {
                    try {
                        return readableProductMapper.convert(item.getProduct(), store, language);
                    } catch (Exception e) {
                        LOGGER.error("Error converting product: " + item.getProduct().getId(), e);
                        return null;
                    }
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());
        readable.setProducts(products);
        
        readable.setItemCount(wishlist.getItems().size());
        return readable;
    }
}
