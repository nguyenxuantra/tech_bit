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
    PRODUCT_NOT_FOUND(1009,"Không tìm thấy sản phẩm này", HttpStatus.NOT_FOUND)
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;
}
