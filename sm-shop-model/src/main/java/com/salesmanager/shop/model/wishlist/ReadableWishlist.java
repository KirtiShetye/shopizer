package com.salesmanager.shop.model.wishlist;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ReadableWishlist - DTO for API responses
 */
@ApiModel(description = "Customer wishlist with favorite products")
public class ReadableWishlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "Wishlist ID")
    private Long id;

    @ApiModelProperty(value = "Customer ID")
    private Long customerId;

    @ApiModelProperty(value = "Wishlist name")
    private String name;

    @ApiModelProperty(value = "List of product IDs in wishlist")
    private List<Long> productIds = new ArrayList<>();

    @ApiModelProperty(value = "Number of items in wishlist")
    private int itemCount;

    @ApiModelProperty(value = "Whether wishlist is public")
    private boolean isPublic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }
}
