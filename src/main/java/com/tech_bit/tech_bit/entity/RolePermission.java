package com.tech_bit.tech_bit.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role_permission")
@Data
public class RolePermission {
    @EmbeddedId
    private RolePermissionId id;

}
