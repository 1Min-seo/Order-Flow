package com.project.orderflow.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDto {
    private String message;
    private Long orderId;     // 주문 ID
    private String tableNumber;  // 테이블 번호
    private int totalPrice;   // 총 주문 금액
}
