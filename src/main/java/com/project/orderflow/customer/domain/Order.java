package com.project.orderflow.customer.domain;

import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import com.project.orderflow.customer.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_table") // 테이블 이름을 명시적으로 설정
@Getter @Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    private Integer quantity;

    private String orderOption;

    @NotNull
    private Long ownerId;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Integer totalAmount;

    @NotNull
    private Long tableId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태 (비완료, 완료)

    private LocalDateTime orderTime; // 주문 시간

    @Builder
    public Order(String foodName, Integer quantity, String orderOption, Long ownerId, Food food, PaymentMethod paymentMethod, Integer totalAmount, Long tableId, OrderStatus orderStatus, LocalDateTime orderTime) {
        this.foodName = foodName;
        this.quantity = quantity;
        this.orderOption = orderOption;
        this.ownerId = ownerId;
        this.food = food;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.tableId = tableId;
        this.orderStatus = orderStatus; // 상태 초기화
        this.orderTime = orderTime; // 주문 시간 설정
    }
}
