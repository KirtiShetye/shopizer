package com.salesmanager.core.business.repositories.wishlist;

import com.salesmanager.core.model.wishlist.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Wishlist Repository - Data access layer for Wishlist
 */
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    /**
     * Find wishlist by customer ID
     */
    @Query("SELECT w FROM Wishlist w LEFT JOIN FETCH w.items WHERE w.customer.id = :customerId")
    Optional<Wishlist> findByCustomerId(@Param("customerId") Long customerId);

    /**
     * Check if wishlist exists for customer
     */
    boolean existsByCustomerId(Long customerId);

    /**
     * Delete wishlist by customer ID
     */
    @Transactional
    @Modifying
    void deleteByCustomerId(Long customerId);

    /**
     * Count items in wishlist
     */
    @Query("SELECT COUNT(wi) FROM WishlistItem wi WHERE wi.wishlist.customer.id = :customerId")
    int countItemsByCustomerId(@Param("customerId") Long customerId);
}
