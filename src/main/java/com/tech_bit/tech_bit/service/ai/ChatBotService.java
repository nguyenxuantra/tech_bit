package com.tech_bit.tech_bit.service.ai;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_bit.tech_bit.entity.Conversation;
import com.tech_bit.tech_bit.entity.Message;
import com.tech_bit.tech_bit.entity.Product;
import com.tech_bit.tech_bit.entity.Users;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.repository.ConversationRepository;
import com.tech_bit.tech_bit.repository.MessageRepository;
import com.tech_bit.tech_bit.repository.ProductRepository;
import com.tech_bit.tech_bit.repository.UserRepository;
import com.tech_bit.tech_bit.util.Sender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tech_bit.tech_bit.dto.response.MessageResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBotService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String chatWithAi(String userMessage){

        // lấy sản phẩm
        List<Product> products = productRepository.topFiveProduct();
        // prompt
        String productContext = buildProductContext(products);
        List<String> chatHistory = getCurrentUserMessages();
        String chatHistoryContext = buildChatHistory(chatHistory);
        String prompt = buildPrompt(productContext, chatHistoryContext, userMessage);
        saveMessage(userMessage);
        return callGemini(prompt);
    }

    private String buildProductContext(List<Product> products) {
        if (products.isEmpty()) {
            return "Hiện tại không có sản phẩm nào.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Danh sách sản phẩm hiện có:\n");

        for (Product p : products) {
            sb.append(String.format(
                    "- Tên: %s | Giá: %,.0f VNĐ | Mô tả: %s | Ảnh sản phẩm: %s\n",
                    p.getName(),
                    p.getPrice(),
                    p.getDescription(),
                    p.getImageUrl()
            ));
        }
        return sb.toString();
    }
    private String buildChatHistory(List<String> messages) {
        if (messages.isEmpty()) {
            return "Chưa có hội thoại trước đó.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Lịch sử hội thoại:\n");

        for (String m : messages) {
            sb.append("Khách hàng: ").append(m).append("\n");
        }
        return sb.toString();
    }
    private String buildPrompt(String productContext,String chatHistoryContext, String userMessage) {
        return """
        Bạn là nhân viên tư vấn bán hàng cho website thương mại điện tử.
        Chỉ sử dụng thông tin trong danh sách sản phẩm bên dưới để tư vấn.
        Không được bịa thêm sản phẩm ngoài danh sách.

        %s
        %s
        Câu hỏi của khách hàng: "%s"
            
                QUY TẮC BẮT BUỘC:
                1. Nếu khách hàng chỉ hỏi CÓ hay KHÔNG, chỉ được trả lời xác nhận + hỏi lại nhu cầu.
                2. KHÔNG tự ý giới thiệu sản phẩm nếu khách chưa yêu cầu.
                3. Chỉ giới thiệu sản phẩm khi khách nói rõ:
                   - Muốn xem sản phẩm
                   - Muốn gợi ý
                   - Muốn tư vấn
                4. Trả lời ngắn gọn, lịch sự, tự nhiên như nhân viên thật.
                5. Không dùng markdown, không quảng cáo dài dòng.
                6. Tư vấn sản phẩm phải gửi đủ tên, giá, mô tả, ảnh sản phẩm 
        """.formatted(productContext, chatHistoryContext, userMessage);
    }
    private String callGemini(String prompt) {
        log.warn("day prompt: {}", prompt);
        try {
            String url =
                    "https://generativelanguage.googleapis.com/v1beta/models/"
                            + "gemini-2.5-flash:generateContent"
                            + "?key=" + apiKey;

            Map<String, Object> body = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "role", "user",
                                    "parts", List.of(
                                            Map.of("text", prompt)
                                    )
                            )
                    )
//                    "generationConfig", Map.of(
//                            "temperature", 0.4,
//                            "maxOutputTokens", 500
//                    )
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(url, entity, String.class);

            JsonNode root =
                    objectMapper.readTree(response.getBody());

            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        } catch (Exception e) {
            return "Xin lỗi, hệ thống AI đang gặp sự cố." + e.getMessage();
        }
    }
    private Integer getCurrentUserId() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return user.getUserId();
    }
    public void saveMessage(String userMessage){
        Integer userId = getCurrentUserId();
        
        // Kiểm tra xem đã có conversation với userId này chưa
        Conversation conversation = conversationRepository.findByUserId(userId.longValue())
                .orElse(null);
        
        // Nếu chưa có conversation thì tạo mới
        if (conversation == null) {
            conversation = new Conversation();
            conversation.setUserId(userId.longValue());
            conversation = conversationRepository.save(conversation);
        }
        
        // Lưu message của người dùng
        Message message = new Message();
        message.setConversation(conversation);
        message.setSender(Sender.USER);
        message.setContent(userMessage);
        message.setCreatedAt(System.currentTimeMillis());
        
        messageRepository.save(message);
    }

    public List<String> getMessagesByUserId(Long userId) {
        // Lấy tất cả messages của user theo userId
        List<Message> messages = messageRepository.findTop10ByConversation_UserIdOrderByCreatedAtDesc(userId);
        
        // Chuyển đổi sang MessageResponse
        return messages.stream()
                .map(Message::getContent).toList();
    }

    public List<String> getCurrentUserMessages() {
        Integer userId = getCurrentUserId();
        return getMessagesByUserId(userId.longValue());
    }

}
