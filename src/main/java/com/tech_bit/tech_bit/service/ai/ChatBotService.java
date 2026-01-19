package com.tech_bit.tech_bit.service.ai;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tech_bit.tech_bit.entity.Product;
import com.tech_bit.tech_bit.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatBotService {
    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${gemini.api.key}")
    private String apiKey;

    public String chatWithAi(String userMessage){
        // lấy sản phẩm
        List<Product> products = productRepository.topFiveProduct();
        // prompt
        String productContext = buildProductContext(products);
        String prompt = buildPrompt(productContext, userMessage);

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
    private String buildPrompt(String productContext, String userMessage) {
        return """
        Bạn là nhân viên tư vấn bán hàng cho website thương mại điện tử.
        Chỉ sử dụng thông tin trong danh sách sản phẩm bên dưới để tư vấn.
        Không được bịa thêm sản phẩm ngoài danh sách.

        %s

        Câu hỏi của khách hàng: "%s"

        Hãy tư vấn ngắn gọn, dễ hiểu, thân thiện.
        """.formatted(productContext, userMessage);
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


}
