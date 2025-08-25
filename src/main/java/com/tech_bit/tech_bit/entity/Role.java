package com.tech_bit.tech_bit.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Column(name = "name",nullable = false, unique = true, length = 100)
    private String name;

}
