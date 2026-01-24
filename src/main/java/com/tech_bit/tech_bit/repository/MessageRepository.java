package com.tech_bit.tech_bit.repository;

import com.tech_bit.tech_bit.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversation_Id(Long conversationId);
    List<Message> findByConversation_UserId(Long userId);
}
