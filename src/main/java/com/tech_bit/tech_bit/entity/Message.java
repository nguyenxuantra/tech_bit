package com.tech_bit.tech_bit.entity;


import com.tech_bit.tech_bit.util.Sender;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // liên kết tới conversation
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    @Enumerated(EnumType.STRING)
    private Sender sender;
    // USER / AI / SYSTEM

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long createdAt;
}
