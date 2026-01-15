package com.tech_bit.tech_bit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coupons {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Integer couponId;

    @Column(name = "code", length = 20, nullable = false, unique = true)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "discount_value", precision = 10, nullable = false)
    private Double discountValue;

    @Column(name = "discount_type", length = 20, nullable = false)
    private String discountType = "FIXED";

    @Column(name = "expiry_date", nullable = false)
    private Long expiryDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_at", nullable = false)
    private Long createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }

}
