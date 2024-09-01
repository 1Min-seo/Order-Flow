package com.project.orderflow.customer.repository;

import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.customer.domain.TableOrder;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableOrderRepository extends JpaRepository<TableOrder, Long> {
    // 특정 테이블의 특정 상태의 모든 주문을 조회
    Optional<TableOrder> findByTableAndStatus(Seat table, OrderStatus status);

    // 특정 테이블의 모든 주문을 상태에 상관없이 조회
    List<TableOrder> findByTable(Seat seat);
}
