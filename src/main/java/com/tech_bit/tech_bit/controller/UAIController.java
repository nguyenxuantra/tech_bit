package com.tech_bit.tech_bit.controller;


import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.request.UChatRequest;
import com.tech_bit.tech_bit.dto.response.MessageResponse;
import com.tech_bit.tech_bit.service.ai.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class UAIController {
    private final ChatBotService chatBotService;

    @PostMapping("/chat")
    public ApiResponse<String> chatbot(@RequestBody UChatRequest uChatRequest) {
        return ApiResponse.<String>builder()
                .code(200)
                .result(chatBotService.chatWithAi(uChatRequest.getMessage()))
                .message("success")
                .build();
    }

//    @GetMapping("/messages")
//    public ApiResponse<List<MessageResponse>> getMessages() {
//        List<MessageResponse> messages = chatBotService.getCurrentUserMessages();
//        return ApiResponse.<List<MessageResponse>>builder()
//                .code(200)
//                .message("Lấy danh sách tin nhắn thành công")
//                .result(messages)
//                .build();
//    }

//    @GetMapping("/messages/{userId}")
//    public ApiResponse<List<MessageResponse>> getMessagesByUserId(@PathVariable Long userId) {
//        List<MessageResponse> messages = chatBotService.getMessagesByUserId(userId);
//        return ApiResponse.<List<MessageResponse>>builder()
//                .code(200)
//                .message("Lấy danh sách tin nhắn thành công")
//                .result(messages)
//                .build();
//    }
}
