package com.tech_bit.tech_bit.repository;

import com.tech_bit.tech_bit.entity.Message;
import com.tech_bit.tech_bit.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findTop10ByConversation_UserIdOrderByCreatedAtDesc(Long userId);
}
