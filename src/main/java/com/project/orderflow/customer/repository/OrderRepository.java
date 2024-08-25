package com.project.orderflow.customer.repository;

import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.customer.domain.TableOrder;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<TableOrder, Long> {
    Optional<TableOrder> findByTableAndStatus(Seat table, OrderStatus status);
}
