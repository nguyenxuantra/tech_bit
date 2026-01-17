package com.tech_bit.tech_bit.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXIST(1000, "User already exist", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1001, "User không tồn tại", HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND(1002, "Không tìm thấy địa chỉ", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1003, "Danh mục không tồn tại", HttpStatus.NOT_FOUND),
    USERNAME_NOT_FOUND(1004, "Không tìm thấy username", HttpStatus.NOT_FOUND),
    PASSWORD_NOT_MATCH(1005, "Mật khẩu không chính xác", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(1006, "You do not have permission", HttpStatus.FORBIDDEN),
    AUTHENTICATED(1007, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    PERMISSION_NOT_FOUND(1008,"Không tìm thấy quyền này", HttpStatus.NOT_FOUND),
    PRODUCT_NOT_FOUND(1009,"Không tìm thấy sản phẩm này", HttpStatus.NOT_FOUND),
    CART_NOT_FOUND(1010,"Không tìm thấy giỏ hàng", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(1011,"Không tìm thấy sản phẩm trong giỏ hàng", HttpStatus.NOT_FOUND),
    INSUFFICIENT_STOCK(1012,"Số lượng sản phẩm không đủ", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1013,"Không tìm thấy đơn hàng", HttpStatus.NOT_FOUND),
    CART_IS_EMPTY(1014,"Giỏ hàng trống", HttpStatus.BAD_REQUEST),
    COUPON_NOT_FOUND(1015,"Không tìm thấy mã giảm giá", HttpStatus.NOT_FOUND),
    COUPON_EXPIRED(1016,"Mã giảm giá đã hết hạn", HttpStatus.BAD_REQUEST),
    COUPON_INACTIVE(1017,"Mã giảm giá không còn hiệu lực", HttpStatus.BAD_REQUEST),
    CATEGORY_IN_USE(1018,"Không thể xóa danh mục vì đang có sản phẩm sử dụng", HttpStatus.BAD_REQUEST),

    ;
    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
