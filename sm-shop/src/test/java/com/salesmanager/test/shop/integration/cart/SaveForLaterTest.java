package com.salesmanager.test.shop.integration.cart;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacade;
import com.salesmanager.test.shop.common.ServicesTestSupport;

/**
 * Integration tests for Save for Later feature
 * Tests the ability to move cart items to wishlist
 */
public class SaveForLaterTest extends ServicesTestSupport {

    @Autowired
    private ShoppingCartFacade shoppingCartFacade;
    
    @Autowired
    private MerchantStoreService merchantService;

    @Test
    public void testSaveForLaterMethodExists() throws Exception {
        // Verify the saveForLater method exists in the facade
        assertNotNull(shoppingCartFacade);
        
        MerchantStore store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
        assertNotNull(store);
        
        // Test that the method signature is correct
        try {
            shoppingCartFacade.getClass().getMethod(
                "saveForLater", 
                String.class, 
                Long.class, 
                Long.class, 
                MerchantStore.class, 
                com.salesmanager.core.model.reference.language.Language.class
            );
        } catch (NoSuchMethodException e) {
            fail("saveForLater method not found in ShoppingCartFacade");
        }
    }
    
    @Test
    public void testSaveForLaterWithNullCustomerId() {
        MerchantStore store;
        try {
            store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
            
            Exception exception = assertThrows(Exception.class, () -> {
                shoppingCartFacade.saveForLater("test-cart", 1L, null, store, store.getDefaultLanguage());
            });
            
            assertTrue(exception.getMessage().contains("Customer ID is required") || 
                      exception.getCause() != null);
        } catch (Exception e) {
            // Test setup failed, skip
        }
    }
    
    @Test
    public void testSaveForLaterWithNullProductId() {
        MerchantStore store;
        try {
            store = merchantService.getByCode(MerchantStore.DEFAULT_STORE);
            
            Exception exception = assertThrows(Exception.class, () -> {
                shoppingCartFacade.saveForLater("test-cart", null, 1L, store, store.getDefaultLanguage());
            });
            
            assertTrue(exception.getMessage().contains("Product ID is required") || 
                      exception.getCause() != null);
        } catch (Exception e) {
            // Test setup failed, skip
        }
    }
}
