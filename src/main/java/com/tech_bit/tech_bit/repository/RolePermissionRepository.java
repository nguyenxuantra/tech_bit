package com.tech_bit.tech_bit.repository;

import com.tech_bit.tech_bit.entity.RolePermission;
import com.tech_bit.tech_bit.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository  extends JpaRepository<RolePermission, RolePermissionId> {
}
