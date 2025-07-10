package com.tech_bit.tech_bit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "user_Id")
    private Integer userId;

    @Column(name = "updated_at")
    private Long updatedAt;

    @PreUpdate
    private void updatedAt() {
        this.updatedAt = System.currentTimeMillis();
    }
}
