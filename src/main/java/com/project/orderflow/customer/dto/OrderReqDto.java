package com.project.orderflow.customer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 고객이 주문하면 저장되는 dto
 */
@Getter
@Builder
public class OrderReqDto {
    private Long tableId;  // 테이블 ID

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime orderAt;  // 주문 시간 (서버에서 자동 생성 가능)

    private List<OrderMenuReqDto> orderMenus;  // 주문할 메뉴 항목과 수량

    public OrderReqDto(Long tableId, LocalDateTime orderAt, List<OrderMenuReqDto> orderMenus) {
        this.tableId = tableId;
        this.orderAt = orderAt;
        this.orderMenus = orderMenus;
    }
}
