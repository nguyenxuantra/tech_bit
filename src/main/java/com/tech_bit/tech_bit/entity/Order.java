package com.tech_bit.tech_bit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "total_amount", precision = 10 ,nullable = false)
    private Double totalAmount;

    @Column(name = "status", length = 20, nullable = false)
    private String status = "PENDING";

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "coupon_id")
    private Integer couponId;

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }
}
