package com.tech_bit.tech_bit.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "supports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Support {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "support_id")
    private Long supportId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "subject", length = 100, nullable = false)
    private String subject;

    @Column(name = "message", nullable = false)
    private String message;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "OPEN";

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }
}
