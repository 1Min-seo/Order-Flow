package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOwnerId(Long ownerId); // ownerId로 조회
}