package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.OptionOrder;
import com.project.orderflow.customer.dto.OptionOrderDto;
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
            description = "ownerId에는 JWT토큰에서 추출한 Id값을 넣어주시고, optionId는 /api/options/list/{ownerId}에서 조회한 옵션 id 값 넣어주시고, tableId는 해당 테이블의 Id값을 넣어주시면 됩니다.  [{\n" +
                    "    \"optionId\": 2,\n" +
                    "    \"quantity\": 1\n" +
                    "  },\n" +
                    "  {\n" +
                    "    \"optionId\": 3,\n" +
                    "    \"quantity\": 1\n" +
                    "  }]이런식으로 보내주시면 여러개 보낼수있어요."
    )
    // 옵션 주문
    @PostMapping("/place-option-orders")
    public ResponseEntity<Void> placeOptionOrders(@RequestBody List<OptionOrderDto> optionOrders,
                                                  @RequestParam Long ownerId,
                                                  @RequestParam Long tableId) {
        optionOrderService.placeOptionOrders(optionOrders, ownerId, tableId);
        return ResponseEntity.ok().build();
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

    @Operation(
            summary = "옵션 주문 상태관리(관리자)",
            description = "해당 옵션주문의 id값"
    )
    // 옵션 주문 상태 변경 (비완료 -> 완료)
    @PutMapping("/complete-option-order/{optionOrderId}")
    public ResponseEntity<OptionOrder> completeOptionOrder(@PathVariable Long optionOrderId) {
        OptionOrder optionOrder = optionOrderService.completeOptionOrder(optionOrderId);
        return ResponseEntity.ok(optionOrder);
    }
}
