package com.project.orderflow.customer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderMenuReqDto {

    private Long menuId;
    private String menuName;
    private int quantity;       // 주문 수량

    public OrderMenuReqDto(Long menuId, String menuName, int quantity) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.quantity = quantity;
    }
}
