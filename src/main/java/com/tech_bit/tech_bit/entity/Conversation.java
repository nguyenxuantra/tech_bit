package com.tech_bit.tech_bit.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "conversations")
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // nếu có user login
    private Long userId;

    private String title; // ví dụ: "Tư vấn laptop gaming"

    private Long createdAt;

    private Long updatedAt;
}

