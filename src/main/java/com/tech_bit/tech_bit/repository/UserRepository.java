package com.tech_bit.tech_bit.repository;

import com.tech_bit.tech_bit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {
    Optional<Users> findByUsername(String username);
}
