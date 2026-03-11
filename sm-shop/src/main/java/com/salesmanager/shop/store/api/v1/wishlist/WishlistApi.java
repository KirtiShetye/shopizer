package com.salesmanager.shop.store.api.v1.wishlist;

import com.salesmanager.core.business.services.wishlist.WishlistService;
import com.salesmanager.core.model.wishlist.Wishlist;
import com.salesmanager.shop.model.wishlist.ReadableWishlist;
import io.swagger.annotations.*;
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
@Api(tags = {"Wishlist API - Manage customer wishlists"})
public class WishlistApi {

    @Inject
    private WishlistService wishlistService;

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
        Wishlist wishlist = wishlistService.getByCustomerId(customerId);
        if (wishlist == null) {
            return ResponseEntity.notFound().build();
        }
        
        ReadableWishlist readable = convertToReadable(wishlist);
        return ResponseEntity.ok(readable);
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
            Wishlist wishlist = wishlistService.addProduct(customerId, productId);
            ReadableWishlist readable = convertToReadable(wishlist);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(readable);
        } catch (Exception e) {
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
        wishlistService.removeProduct(customerId, productId);
        return ResponseEntity.noContent().build();
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
        wishlistService.clearWishlist(customerId);
        return ResponseEntity.noContent().build();
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
        int count = wishlistService.getWishlistCount(customerId);
        return ResponseEntity.ok(count);
    }

    private ReadableWishlist convertToReadable(Wishlist wishlist) {
        ReadableWishlist readable = new ReadableWishlist();
        readable.setId(wishlist.getId());
        readable.setCustomerId(wishlist.getCustomer().getId());
        readable.setProductIds(
            wishlist.getItems().stream()
                .map(item -> item.getProduct().getId())
                .collect(Collectors.toList())
        );
        readable.setItemCount(wishlist.getItems().size());
        return readable;
    }
}
