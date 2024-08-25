package com.project.orderflow.customer.domain;

import com.project.orderflow.admin.domain.Seat;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 주문 정보_ 테이블번호 / 메뉴 / 총 가격
 */
@Entity
@Getter
@NoArgsConstructor
public class TableOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="table_order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="table_id")
    private Seat table;   //테이블번호

    private LocalDateTime orderAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "table_order_id") // 단방향 연관 관계
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalAmount; // 주문 총액

    @Builder
    public TableOrder(Seat table, LocalDateTime orderAt, OrderStatus status) {
        this.table = table;
        this.orderAt = LocalDateTime.now();
        this.status = status;
        this.totalAmount = 0;
    }
    // 새로운 OrderMenu 추가 및 총액 업데이트 메서드
    public void addOrderMenu(OrderMenu orderMenu) {
        this.orderMenus.add(orderMenu);
        this.totalAmount += orderMenu.getOrderPrice(); // 총액 업데이트
    }
    // 여러 OrderMenus 추가 메서드
    public void addOrderMenus(List<OrderMenu> orderMenuList) {
        for (OrderMenu orderMenu : orderMenuList) {
            this.addOrderMenu(orderMenu);
        }
    }
}
