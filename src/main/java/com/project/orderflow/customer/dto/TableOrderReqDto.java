package com.project.orderflow.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.orderflow.customer.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TableOrderReqDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime orderAt;

    private OrderStatus status;

    private int totalPrice;

    private List<OrderMenuReqDto> orderMenus;
}
