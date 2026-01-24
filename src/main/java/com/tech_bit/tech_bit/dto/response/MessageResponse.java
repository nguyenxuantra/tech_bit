package com.tech_bit.tech_bit.dto.response;

import com.tech_bit.tech_bit.util.Sender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {
    private Long id;
    private Long conversationId;
    private Sender sender;
    private String content;
    private Long createdAt;
}
