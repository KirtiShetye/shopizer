package com.salesmanager.core.business.services.wishlist;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.wishlist.WishlistRepository;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.wishlist.Wishlist;
import com.salesmanager.core.model.wishlist.WishlistItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Wishlist Service Implementation
 * Business logic for managing customer wishlists
 */
@Service("wishlistService")
public class WishlistServiceImpl extends SalesManagerEntityServiceImpl<Long, Wishlist> implements WishlistService {

    private final WishlistRepository wishlistRepository;

    @Inject
    private CustomerService customerService;

    @Inject
    private ProductService productService;

    @Inject
    public WishlistServiceImpl(WishlistRepository wishlistRepository) {
        super(wishlistRepository);
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Wishlist getByCustomerId(Long customerId) {
        return wishlistRepository.findByCustomerId(customerId).orElse(null);
    }

    @Override
    @Transactional
    public Wishlist addProduct(Long customerId, Long productId) throws ServiceException {
        // Get or create wishlist
        Wishlist wishlist = getByCustomerId(customerId);
        if (wishlist == null) {
            Customer customer = customerService.getById(customerId);
            if (customer == null) {
                throw new ServiceException("Customer not found: " + customerId);
            }
            wishlist = new Wishlist(customer);
        }

        // Check if product already in wishlist
        boolean exists = wishlist.getItems().stream()
            .anyMatch(item -> item.getProduct().getId().equals(productId));
        
        if (!exists) {
            Product product = productService.getById(productId);
            if (product == null) {
                throw new ServiceException("Product not found: " + productId);
            }
            
            WishlistItem item = new WishlistItem(wishlist, product);
            wishlist.addItem(item);
        }

        return wishlistRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void removeProduct(Long customerId, Long productId) {
        Wishlist wishlist = getByCustomerId(customerId);
        if (wishlist != null) {
            WishlistItem itemToRemove = wishlist.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
            
            if (itemToRemove != null) {
                wishlist.removeItem(itemToRemove);
                wishlistRepository.save(wishlist);
            }
        }
    }

    @Override
    @Transactional
    public void clearWishlist(Long customerId) {
        Wishlist wishlist = getByCustomerId(customerId);
        if (wishlist != null) {
            wishlist.getItems().clear();
            wishlistRepository.save(wishlist);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public int getWishlistCount(Long customerId) {
        return wishlistRepository.countItemsByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isProductInWishlist(Long customerId, Long productId) {
        Wishlist wishlist = getByCustomerId(customerId);
        if (wishlist == null) {
            return false;
        }
        return wishlist.getItems().stream()
            .anyMatch(item -> item.getProduct().getId().equals(productId));
    }
}
