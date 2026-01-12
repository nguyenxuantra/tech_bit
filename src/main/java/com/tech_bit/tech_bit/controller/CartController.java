package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.request.CartItemRequest;
import com.tech_bit.tech_bit.dto.request.UpdateCartItemRequest;
import com.tech_bit.tech_bit.dto.response.CartResponse;
import com.tech_bit.tech_bit.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/items")
    public ApiResponse<CartResponse> addItemToCart(@RequestBody CartItemRequest request) {
        CartResponse cartResponse = cartService.addItemToCart(request);
        return ApiResponse.<CartResponse>builder()
                .code(200)
                .message("Thêm sản phẩm vào giỏ hàng thành công")
                .result(cartResponse)
                .build();
    }

    // Sửa số lượng sản phẩm trong giỏ hàng
    @PutMapping("/items/{cartItemId}")
    public ApiResponse<CartResponse> updateCartItem(
            @PathVariable Integer cartItemId,
            @RequestBody UpdateCartItemRequest request) {
        CartResponse cartResponse = cartService.updateCartItem(cartItemId, request.getQuantity());
        return ApiResponse.<CartResponse>builder()
                .code(200)
                .message("Cập nhật giỏ hàng thành công")
                .result(cartResponse)
                .build();
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/items/{cartItemId}")
    public ApiResponse<Void> removeCartItem(@PathVariable Integer cartItemId) {
        cartService.removeCartItem(cartItemId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa sản phẩm khỏi giỏ hàng thành công")
                .build();
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping
    public ApiResponse<Void> clearCart() {
        cartService.clearCart();
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa giỏ hàng thành công")
                .build();
    }

    // Lấy thông tin giỏ hàng
    @GetMapping
    public ApiResponse<CartResponse> getCart() {
        CartResponse cartResponse = cartService.getCart();
        return ApiResponse.<CartResponse>builder()
                .code(200)
                .message("Success")
                .result(cartResponse)
                .build();
    }
}
