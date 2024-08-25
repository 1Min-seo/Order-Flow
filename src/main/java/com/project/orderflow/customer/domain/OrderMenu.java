package com.project.orderflow.customer.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문한 메뉴 목록
 */
@Entity
@Getter
@NoArgsConstructor
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="menu_id")
    private Menu menu;          // 음식 메뉴

    @Column(nullable = false)
    private int quantity;       // 주문 수량

    private int orderPrice;     //주문 가격(total)
    private int count;          //주문 수량

    @Builder
    public OrderMenu(Menu menu, int quantity, int orderPrice, int count) {
        this.menu = menu;
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}

