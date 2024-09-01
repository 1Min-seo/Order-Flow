package com.project.orderflow.customer.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    COOK("음식 조리"),
    PAY("계산 필요"),
    CLEAR("제공 완료"),
    ORDERED("주문 접수"),
    CART("장바구니");

    private final String key;
}
