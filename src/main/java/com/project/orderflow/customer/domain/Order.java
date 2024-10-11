package com.project.orderflow.customer.domain;

import com.project.orderflow.admin.domain.Food;
import com.project.orderflow.customer.domain.enums.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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


    @NotNull
    private Long ownerId; // 매장 ID로 구분

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // 결제 방식 (현금, 카드, 카카오페이)

    private Integer totalAmount; // 결제 금액

    @NotNull
    private Long tableId; // 테이블 ID 추가

    @Builder
    public Order(String foodName, Integer quantity, Long ownerId, Food food, PaymentMethod paymentMethod, Integer totalAmount, Long tableId) { // tableId 추가
        this.foodName = foodName;
        this.quantity = quantity;
        this.ownerId = ownerId;
        this.food = food;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.tableId = tableId; // tableId 저장
    }
}
