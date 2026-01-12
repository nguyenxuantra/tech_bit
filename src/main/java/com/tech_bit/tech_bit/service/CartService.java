package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.CartItemRequest;
import com.tech_bit.tech_bit.dto.response.CartResponse;

public interface CartService {
    CartResponse addItemToCart(CartItemRequest request);
    CartResponse updateCartItem(Integer cartItemId, Integer quantity);
    void removeCartItem(Integer cartItemId);
    void clearCart();
    CartResponse getCart();
}
