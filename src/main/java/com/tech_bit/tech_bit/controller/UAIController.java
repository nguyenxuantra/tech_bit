package com.tech_bit.tech_bit.controller;


import com.cloudinary.Api;
import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.request.SaveMessageRequest;
import com.tech_bit.tech_bit.dto.request.UChatRequest;
import com.tech_bit.tech_bit.service.MessageService;
import com.tech_bit.tech_bit.service.ai.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class UAIController {
    private final ChatBotService chatBotService;
    private final MessageService messageService;

    @PostMapping("/chat")
    public ApiResponse<String> chatbot(@RequestBody UChatRequest uChatRequest) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(chatBotService.chatWithAi(uChatRequest.getMessage()))
                .message("success")
                .build();
    }

    @PostMapping("/messages/save")
    public ApiResponse<Void> saveUserMessage(@RequestBody SaveMessageRequest request) {
        messageService.saveUserMessage(request);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Lưu tin nhắn thành công")
                .build();
    }
}
