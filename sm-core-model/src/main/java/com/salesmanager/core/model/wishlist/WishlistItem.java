package com.salesmanager.core.model.wishlist;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * WishlistItem Entity - Individual product in a wishlist
 * 
 * Database Table: WISHLIST_ITEM
 * Relationships:
 * - Many-to-One with Wishlist
 * - Many-to-One with Product
 */
@Entity
@Table(name = "WISHLIST_ITEM")
public class WishlistItem extends SalesManagerEntity<Long, WishlistItem> implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "WISHLIST_ITEM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WISHLIST_ID", nullable = false)
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column(name = "ADDED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addedDate;

    @Column(name = "PRIORITY")
    private Integer priority = 0;

    @Column(name = "NOTES", length = 500)
    private String notes;

    public WishlistItem() {
        this.addedDate = new Date();
    }

    public WishlistItem(Wishlist wishlist, Product product) {
        this.wishlist = wishlist;
        this.product = product;
        this.addedDate = new Date();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
