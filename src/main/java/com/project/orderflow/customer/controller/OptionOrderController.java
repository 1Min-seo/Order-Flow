package com.project.orderflow.customer.controller;

import com.project.orderflow.customer.domain.OptionOrder;
import com.project.orderflow.customer.dto.OptionOrderDto;
import com.project.orderflow.customer.service.OptionOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "옵션 주문 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "500", description = "서버 오류")
    })
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "옵션 주문 내역 조회 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionOrder.class))),
            @ApiResponse(responseCode = "404", description = "옵션 주문 내역을 찾을 수 없음", content = @Content) // 빈 콘텐츠로 명시
    })
    @GetMapping("/history/{ownerId}")
    public ResponseEntity<List<OptionOrder>> getOptionOrderHistory(@PathVariable Long ownerId) {
        List<OptionOrder> orders = optionOrderService.getOptionOrderHistory(ownerId);
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "옵션 주문 상태관리(관리자)",
            description = "해당 옵션 주문의 id값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "옵션 주문 상태 완료로 변경 성공", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OptionOrder.class))),
            @ApiResponse(responseCode = "404", description = "옵션 주문을 찾을 수 없음", content = @Content) // 빈 콘텐츠로 명시
    })
    @PutMapping("/complete-option-order/{optionOrderId}")
    public ResponseEntity<OptionOrder> completeOptionOrder(@PathVariable Long optionOrderId) {
        OptionOrder optionOrder = optionOrderService.completeOptionOrder(optionOrderId);
        return ResponseEntity.ok(optionOrder);
    }

    @Operation(
            summary = "옵션 주문 삭제",
            description = "옵션 주문 id값"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "옵션 주문 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "옵션 주문을 찾을 수 없음", content = @Content) // 빈 콘텐츠로 명시
    })
    @DeleteMapping("/delete-option-order/{optionOrderId}")
    public ResponseEntity<Void> deleteOptionOrder(@PathVariable Long optionOrderId) {
        optionOrderService.deleteOptionOrder(optionOrderId);
        return ResponseEntity.noContent().build();
    }
}
