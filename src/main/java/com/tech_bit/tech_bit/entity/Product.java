package com.tech_bit.tech_bit.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "rating")
    private Double rating ;

    @Column(name = "brand")
    private String brand;

    @Column(name = "public_id")
    private String publicId;

    @Column(name = "created_at")
    private Long createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Categories categories;

    @PrePersist
    private void createdAt() {
        this.createdAt = System.currentTimeMillis();
    }
}
