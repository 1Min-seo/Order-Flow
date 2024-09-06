package com.project.orderflow.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionOrderDto {

    private Long optionId;  // 옵션 ID
    private int quantity;   // 수량

}