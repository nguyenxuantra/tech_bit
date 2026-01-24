//package com.tech_bit.tech_bit.service;
//
//import com.tech_bit.tech_bit.dto.request.SaveMessageRequest;
//import com.tech_bit.tech_bit.entity.Conversation;
//import com.tech_bit.tech_bit.entity.Message;
//import com.tech_bit.tech_bit.repository.ConversationRepository;
//import com.tech_bit.tech_bit.repository.MessageRepository;
//import com.tech_bit.tech_bit.util.Sender;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class MessageServiceImpl implements MessageService {
//
//    MessageRepository messageRepository;
//    ConversationRepository conversationRepository;
//
//    @Override
//    @Transactional
//    public void saveUserMessage(SaveMessageRequest request) {
//        Conversation conversation;
//
//        // Nếu có conversationId, tìm conversation hiện có
//        if (request.getConversationId() != null) {
//            conversation = conversationRepository.findById(request.getConversationId())
//                    .orElseThrow(() -> new RuntimeException("Conversation not found"));
//        } else {
//            // Tạo conversation mới nếu chưa có
//            conversation = new Conversation();
//            conversation.setUserId(request.getUserId());
//            long currentTime = System.currentTimeMillis();
//            conversation = conversationRepository.save(conversation);
//        }
//
//        // Tạo và lưu tin nhắn của người dùng
//        Message message = new Message();
//        message.setConversation(conversation);
//        message.setSender(Sender.USER);
//        message.setContent(request.getContent());
//        message.setCreatedAt(System.currentTimeMillis());
//
//        messageRepository.save(message);
//
//        // Cập nhật updatedAt của conversation
//        conversationRepository.save(conversation);
//    }
//}
