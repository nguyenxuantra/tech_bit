package com.tech_bit.tech_bit.service.admin.image;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.tech_bit.tech_bit.dto.response.UploadImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public UploadImageResponse uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", "techbit",
                            "resource_type", "image"
                    )
            );
            return UploadImageResponse.builder()
                    .secureUrl(uploadResult.get("secure_url").toString())
                    .publicId(uploadResult.get("public_id").toString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Upload image failed" +  e.getMessage(), e);
        }
    }

    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "image"
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("Delete image failed", e);
        }
    }
}
