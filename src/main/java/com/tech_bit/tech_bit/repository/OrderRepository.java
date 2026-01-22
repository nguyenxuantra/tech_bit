package com.tech_bit.tech_bit.repository;


import com.tech_bit.tech_bit.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Integer userId);
    List<Order> findByStatus(String status);
//    Page<Order> findByUserId(Integer userId, Pageable pageable);
    Page<Order> findByStatus( String status, Pageable pageable);
}
