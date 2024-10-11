package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.OptionOrder;
import com.project.orderflow.customer.service.OptionOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/option-orders")
@Tag(name = "옵션주문", description = "옵션 주문하기(고객) / 옵션 주문 내역 조회(관리자)")
public class OptionOrderController {

    private final OptionOrderService optionOrderService;

    public OptionOrderController(OptionOrderService optionOrderService) {
        this.optionOrderService = optionOrderService;
    }


    @Operation(
            summary = "옵션 주문(고객)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어주시고, optionId는 /api/options/list/{ownerId}에서 조회한 옵션 id 값 넣어주시고, tableId는 해당 테이블의 Id값을 넣어주시면 됩니다."
    )
    // 옵션 주문하기
    @PostMapping("/place-order")
    public ResponseEntity<OptionOrder> placeOptionOrder(
            @RequestParam Long ownerId,
            @RequestParam Long optionId,
            @RequestParam Integer quantity,
            @RequestParam Long tableId // 테이블 ID 추가
    ) {
        OptionOrder optionOrder = optionOrderService.placeOptionOrder(ownerId, optionId, quantity, tableId); // tableId 전달
        return ResponseEntity.ok(optionOrder);
    }


    @Operation(
            summary = "옵션 주문내역 조회(관리자)",
            description = "ownerId에는 JWT토큰에서 추출한 Id값"
    )
    // 옵션 주문 내역 조회
    @GetMapping("/history/{ownerId}")
    public ResponseEntity<List<OptionOrder>> getOptionOrderHistory(@PathVariable Long ownerId) {
        List<OptionOrder> orders = optionOrderService.getOptionOrderHistory(ownerId);
        return ResponseEntity.ok(orders);
    }
}
