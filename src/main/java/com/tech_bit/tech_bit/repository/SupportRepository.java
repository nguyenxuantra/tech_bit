package com.tech_bit.tech_bit.repository;


import com.tech_bit.tech_bit.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportRepository extends JpaRepository<Support, Integer> {
}
