package com.salesmanager.core.model.wishlist;

import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Wishlist Entity - Stores customer's favorite products
 * 
 * Database Table: WISHLIST
 * Relationships:
 * - One-to-One with Customer
 * - One-to-Many with WishlistItem
 */
@Entity
@Table(name = "WISHLIST", uniqueConstraints = @UniqueConstraint(columnNames = {"CUSTOMER_ID"}))
public class Wishlist extends SalesManagerEntity<Long, Wishlist> implements Auditable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "WISHLIST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "wishlist", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<WishlistItem> items = new ArrayList<>();

    @Embedded
    private AuditSection auditSection = new AuditSection();

    @Column(name = "WISHLIST_NAME")
    private String name;

    @Column(name = "IS_PUBLIC")
    private boolean isPublic = false;

    public Wishlist() {
    }

    public Wishlist(Customer customer) {
        this.customer = customer;
        this.name = "My Wishlist";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<WishlistItem> getItems() {
        return items;
    }

    public void setItems(List<WishlistItem> items) {
        this.items = items;
    }

    public void addItem(WishlistItem item) {
        item.setWishlist(this);
        this.items.add(item);
    }

    public void removeItem(WishlistItem item) {
        this.items.remove(item);
        item.setWishlist(null);
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
