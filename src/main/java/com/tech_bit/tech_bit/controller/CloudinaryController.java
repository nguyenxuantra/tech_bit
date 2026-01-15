package com.tech_bit.tech_bit.controller;


import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.response.UploadImageResponse;
import com.tech_bit.tech_bit.service.admin.image.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cloudinary/image")
@RequiredArgsConstructor
public class CloudinaryController {
    private final CloudinaryService cloudinaryService;

    @PostMapping
    public ApiResponse<UploadImageResponse> uploadImage(
            @RequestParam("file") MultipartFile file) {

        cloudinaryService.uploadImage(file);
        return ApiResponse.<UploadImageResponse>builder()
                .code(200)
                .result(cloudinaryService.uploadImage(file))
                .message("Tạo danh mục thành công")
                .build();
    }


    @DeleteMapping
    public ApiResponse<Void> deleteImage(
            @RequestParam("public_id") String publicId
    ) {
        cloudinaryService.deleteImage(publicId);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Xóa ảnh thành công")
                .build();
    }
}
