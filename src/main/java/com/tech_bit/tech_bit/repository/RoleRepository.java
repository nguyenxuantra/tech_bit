package com.tech_bit.tech_bit.repository;


import com.tech_bit.tech_bit.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
}
