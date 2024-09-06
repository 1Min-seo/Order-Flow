package com.project.orderflow.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionOrderReqDto {

    private String tableNumber;
    // 옵션 주문 목록
    private List<OptionOrderDto> optionOrders;
}