package com.tech_bit.tech_bit.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ElementCollection
    @Column(name = "role")
    private Set<String> roles;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updateAt;

    @PrePersist
    private void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }
    @PreUpdate
    private void onUpdate() {
        this.updateAt = System.currentTimeMillis();
    }
}
