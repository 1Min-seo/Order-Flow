package com.project.orderflow.customer.domain;
import java.time.LocalDateTime;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 주문한 메뉴 목록
 */
@Entity
@Getter
@NoArgsConstructor
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="menu_id")
    private Menu menu;

    private int quantity;

    private int orderPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name="table_order_id", nullable = false)
    private TableOrder tableOrder;

    @Builder
    public OrderMenu(Menu menu, int quantity, int orderPrice, OrderStatus status, TableOrder tableOrder) {
        this.menu = menu;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.status = status;
        this.tableOrder = tableOrder;
    }

    // 수량과 가격을 업데이트하는 메서드
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        this.orderPrice = this.menu.getPrice() * newQuantity;
    }

    // 상태를 주문으로 변경하고 주문 시간을 저장
    public void changeToOrder() {
        this.status = OrderStatus.ORDERED;
    }
}
