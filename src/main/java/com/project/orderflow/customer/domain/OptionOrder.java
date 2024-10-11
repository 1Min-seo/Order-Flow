package com.project.orderflow.customer.domain;

import com.project.orderflow.customer.domain.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OptionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "id")
    private ItemOption itemOption;

    @NotNull
    private Long ownerId;

    @NotNull
    private Integer quantity;

    @NotNull
    private Long tableId; // 테이블 ID 추가

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 옵션 주문 상태 (비완료, 완료)

    private LocalDateTime orderTime; // 옵션 주문 시간

    @Builder
    public OptionOrder(ItemOption itemOption, Long ownerId, Integer quantity, Long tableId, OrderStatus orderStatus, LocalDateTime orderTime) {
        this.itemOption = itemOption;
        this.ownerId = ownerId;
        this.quantity = quantity;
        this.tableId = tableId;
        this.orderStatus = orderStatus; // 상태 초기화
        this.orderTime = orderTime; // 주문 시간 설정
    }
}