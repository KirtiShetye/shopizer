package com.salesmanager.core.business.services.wishlist;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.wishlist.Wishlist;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;

/**
 * Wishlist Service Interface
 */
public interface WishlistService extends SalesManagerEntityService<Long, Wishlist> {

    /**
     * Get wishlist by customer ID
     */
    Wishlist getByCustomerId(Long customerId);

    /**
     * Add product to wishlist
     */
    Wishlist addProduct(Long customerId, Long productId) throws ServiceException;

    /**
     * Remove product from wishlist
     */
    void removeProduct(Long customerId, Long productId);

    /**
     * Clear all items from wishlist
     */
    void clearWishlist(Long customerId);

    /**
     * Get wishlist item count
     */
    int getWishlistCount(Long customerId);

    /**
     * Check if product is in wishlist
     */
    boolean isProductInWishlist(Long customerId, Long productId);
}
