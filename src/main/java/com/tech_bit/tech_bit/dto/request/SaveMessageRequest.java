package com.tech_bit.tech_bit.dto.request;

import lombok.Data;

@Data
public class SaveMessageRequest {
    private Long conversationId;
    private String content;
    private Long userId; // Optional: nếu muốn tạo conversation mới
    private String title; // Optional: cho conversation mới
}
