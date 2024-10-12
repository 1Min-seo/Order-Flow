package com.project.orderflow.customer.repository;

import com.project.orderflow.customer.domain.OptionOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionOrderRepository extends JpaRepository<OptionOrder, Long> {
    List<OptionOrder> findByOwnerId(Long ownerId); // 매장 ID로 옵션 주문 조회
}