package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.CartItemRequest;
import com.tech_bit.tech_bit.dto.response.CartItemResponse;
import com.tech_bit.tech_bit.dto.response.CartResponse;
import com.tech_bit.tech_bit.entity.Cart;
import com.tech_bit.tech_bit.entity.CartItem;
import com.tech_bit.tech_bit.entity.Product;
import com.tech_bit.tech_bit.entity.User;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.repository.CartItemRepository;
import com.tech_bit.tech_bit.repository.CartRepository;
import com.tech_bit.tech_bit.repository.ProductRepository;
import com.tech_bit.tech_bit.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    private Integer getCurrentUserId() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return user.getUserId();
    }

    private Cart getOrCreateCart(Integer userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .userId(userId)
                            .build();
                    return cartRepository.save(cart);
                });
    }

    @Override
    @Transactional
    public CartResponse addItemToCart(CartItemRequest request) {
        Integer userId = getCurrentUserId();
        Cart cart = getOrCreateCart(userId);

        // Validate product exists
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        // Check stock availability
        if (product.getStock() < request.getQuantity()) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        // Check if item already exists in cart
        CartItem existingItem = cartItemRepository.findByCartIdAndProductId(cart.getCartId(), request.getProductId())
                .orElse(null);

        if (existingItem != null) {
            // Update quantity if item exists
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            if (product.getStock() < newQuantity) {
                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
            }
            existingItem.setQuantity(newQuantity);
            cartItemRepository.save(existingItem);
        } else {
            // Create new cart item
            CartItem cartItem = CartItem.builder()
                    .cartId(cart.getCartId())
                    .productId(request.getProductId())
                    .quantity(request.getQuantity())
                    .build();
            cartItemRepository.save(cartItem);
        }

        // Update cart timestamp
        cartRepository.save(cart);

        return getCart();
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(Integer cartItemId, Integer quantity) {
        Integer userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        // Verify cart item belongs to user's cart
        if (!cartItem.getCartId().equals(cart.getCartId())) {
            throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
        }

        // Validate product stock
        Product product = productRepository.findById(cartItem.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (product.getStock() < quantity) {
            throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        // Update cart timestamp
        cartRepository.save(cart);

        return getCart();
    }

    @Override
    @Transactional
    public void removeCartItem(Integer cartItemId) {
        Integer userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        // Verify cart item belongs to user's cart
        if (!cartItem.getCartId().equals(cart.getCartId())) {
            throw new AppException(ErrorCode.CART_ITEM_NOT_FOUND);
        }

        cartItemRepository.delete(cartItem);

        // Update cart timestamp
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart() {
        Integer userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        cartItemRepository.deleteByCartId(cart.getCartId());
        // Update cart timestamp
        cartRepository.save(cart);
    }

    @Override
    public CartResponse getCart() {
        Integer userId = getCurrentUserId();
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = Cart.builder()
                            .userId(userId)
                            .build();
                    return cartRepository.save(newCart);
                });

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
        List<CartItemResponse> itemResponses = cartItems.stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
                    return CartItemResponse.builder()
                            .cartItemId(item.getCartItemId())
                            .productId(item.getProductId())
                            .productName(product.getName())
                            .productPrice(product.getPrice())
                            .productImageUrl(product.getImageUrl())
                            .quantity(item.getQuantity())
                            .createdAt(item.getCreatedAt())
                            .build();
                })
                .collect(Collectors.toList());

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .userId(cart.getUserId())
                .items(itemResponses)
                .updatedAt(cart.getUpdatedAt())
                .build();
    }
}
