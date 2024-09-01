package com.project.orderflow.customer.repository;

import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.customer.domain.OrderMenu;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
    /**
     * 특정 테이블에 대한 장바구니 상태의 항목 조회
     *
     * @param table 테이블
     * @param status 장바구니 상태
     * @return 특정 테이블의 장바구니 상태의 항목 목록
     */
    List<OrderMenu> findByTableOrder_TableAndStatus(Seat table, OrderStatus status);

}
